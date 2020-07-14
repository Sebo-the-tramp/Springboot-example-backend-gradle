package it.refill.backend.controllers.general;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.users.Customer;
import it.refill.backend.models.users.ERole;
import it.refill.backend.models.users.Role;
import it.refill.backend.models.users.User;
import it.refill.backend.models.users.UserAuth;
import it.refill.backend.models.users.VerificationToken;
import it.refill.backend.payload.request.LoginRequest;
import it.refill.backend.payload.request.SignupRequest;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.list.ProductListRepository;
import it.refill.backend.repository.users.CustomerRepository;
import it.refill.backend.repository.users.RoleRepository;
import it.refill.backend.repository.users.UserAuthRepository;
import it.refill.backend.repository.users.UserRepository;
import it.refill.backend.security.jwt.JwtUtils;
import it.refill.backend.security.services.UserDetailsImpl;
import it.refill.backend.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired 
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService service;

    @Autowired
    ProductListRepository productListRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;    

    @Operation(summary = "SignIn User", description = "Sign a user in returning some data about the login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succesfully logged in", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        
        } catch (BadCredentialsException e){
            return new ResponseEntity<String>("The data were not right", HttpStatus.NOT_FOUND);
        }
    
    }

    @Operation(summary = "Signup User", description = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully create a new user in"),
        @ApiResponse(responseCode = "409", description = "User with this email already exists")
    })
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) {

        //check if a user with the same email exists
        if (userAuthRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }        

        // Create and save into the db new customer entity
        Customer newCustomer = new Customer(signUpRequest.getFirstName(), signUpRequest.getLastName(), "some link", signUpRequest.getTelephone(), "+39", new Date(System.currentTimeMillis()));
        customerRepository.saveAndFlush(newCustomer);

        // Create and save into the db the user 
        User newUser = new User("customer", null, newCustomer, null);    
        userRepository.saveAndFlush(newUser);

        //getting the roles for the user --> can be improved
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        
        // Create and saving into the db new user's account
        UserAuth authUser = new UserAuth(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        authUser.setRoles(roles);
        userAuthRepository.save(authUser);

        logger.info(authUser.getId().toString());
        
        //send confirmation email
        service.confirmRegistration(authUser, request.getContextPath());

        return new ResponseEntity<>("User created succesfuly", HttpStatus.CREATED);
    }

    @Operation(summary = "Confirm mail", description = "Click the link in order to confirm the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succesfully confirmed email", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "400", description = "The token is not there"),
        @ApiResponse(responseCode = "406", description = "The token is expired")
    })
    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(@Valid @RequestParam("token") String token) {
        
        VerificationToken verificationToken = service.getVerificationToken(token);

        //if the token is not there return an error message
        if (verificationToken == null) {

            return new ResponseEntity<String>("The token is not there", HttpStatus.BAD_REQUEST);
            // return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        //check if the token has expired and if so, send an error message
        UserAuth user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<String>("The token is already expired", HttpStatus.NOT_ACCEPTABLE);
        }

        

        //if everything went good, the user is enabled
        user.setEnabled(true);
        service.deleteVerificationToken(verificationToken);

        //adding a default list called cart only when the user has confirmed its identity in order to make less usage
        productListRepository.addProductList("Cart", "The shopping cart", true, user.getId());

        // service.saveRegisteredUser(user);
        return new ResponseEntity<String>("Your account is now ready", HttpStatus.ACCEPTED);
    }
}