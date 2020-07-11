package it.refill.backend.controllers.supplier;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.address.Address;
import it.refill.backend.models.users.ERole;
import it.refill.backend.models.users.Role;
import it.refill.backend.models.users.Supplier;
import it.refill.backend.models.users.User;
import it.refill.backend.models.users.UserAuth;
import it.refill.backend.payload.request.SupplierPostNew;
import it.refill.backend.repository.address.AddressRepository;
import it.refill.backend.repository.address.CountyRepository;
import it.refill.backend.repository.address.MunicipalityRepository;
import it.refill.backend.repository.address.NationalityRepository;
import it.refill.backend.repository.products.ProductRepository;
import it.refill.backend.repository.users.RoleRepository;
import it.refill.backend.repository.users.SupplierRepository;
import it.refill.backend.repository.users.UserAuthRepository;
import it.refill.backend.repository.users.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired
    MunicipalityRepository municipalityRepository;

    @Autowired
    NationalityRepository nationalityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired 
    ProductRepository productRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(SupplierController.class);

    /* ############### ROLE SUPPLIER ################*/

    
    @Operation(summary = "Get supplier", description = "Get the details of the supplier who asked them")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                        
    })
    @GetMapping("/self")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<User> getSingleSupplier(HttpServletRequest req) {

        Long user_id = (Long) req.getAttribute("user_id");        

        User prova = userRepository.getOne(user_id);        

        return new ResponseEntity<User>(prova, HttpStatus.ACCEPTED);        
    }

    /* ############### ROLE ADMIN ################*/ 

    @Operation(summary = "Get supplier", description = "Get supplier's details from some provided supplier_id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                
        @ApiResponse(responseCode = "404", description = "Supplier not found"), 
    })
    @GetMapping(value="", params = {"supplierId"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSupplierById(@RequestParam Long supplierId){

        try{
            Supplier requestedSupplier = supplierRepository.getOne(supplierId);

            return new ResponseEntity<Supplier>(requestedSupplier, HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<String>("Supplier not found", HttpStatus.ACCEPTED);
        }     
    }

    @Operation(summary = "Add supplier", description = "Create new Supplier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                
        @ApiResponse(responseCode = "400", description = "Some data was not good formatted"), 
        @ApiResponse(responseCode = "409", description = "The supplier with the specified email already exist"),
        @ApiResponse(responseCode = "500", description = "Some wired error"), 
    })
    @Transactional
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSupplier(@Valid @RequestBody SupplierPostNew supplierData, HttpServletRequest request) {

        Supplier newSupplier = supplierData.getSupplier();

        UserAuth newUserAuth = supplierData.getUserAuth();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_SUPPLIER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        // encode password
        newUserAuth.setPassword(encoder.encode(newUserAuth.getPassword()));
        newUserAuth.setRoles(roles);


        // encode password
        newUserAuth.setPassword(encoder.encode(newUserAuth.getPassword()));

        User newUser = null;

        try {
            // check if user auth exists

            if (!userAuthRepository.findByUsername(newUserAuth.getUsername()).isPresent()) {
                userAuthRepository.saveAndFlush(newUserAuth);

                Set<Address> newAddresses = new HashSet<Address>();

                try {
                    supplierData.getAddresses().forEach((address) -> {                        

                        // check if the address already exists
                        newAddresses.add(new Address(address.getAddressNumber(), address.getAddressStreet(),
                                address.getType(), municipalityRepository.getOne(address.getMunicipalityId()),
                                nationalityRepository.getOne(address.getNationalityId()),
                                countyRepository.getOne(address.getCountyId())));
                    });
                } catch (Exception e) {
                    return new ResponseEntity<String>(e.getMessage() + e.getCause(), HttpStatus.BAD_REQUEST);
                }                

                addressRepository.saveAll(newAddresses);

                supplierRepository.save(newSupplier);

                newUser = new User(supplierData.getType(), newSupplier, null, newAddresses);                

                userRepository.saveAndFlush(newUser);

            } else {
                return new ResponseEntity<String>("the user already exists", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

        
    @DeleteMapping("")    
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSupplier(@RequestParam Long supplierId){
        try{
            supplierRepository.deleteById(supplierId);
            return new ResponseEntity<>("Deleted succesfully", HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}