package it.refill.backend.controllers.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.refill.backend.models.users.Customer;
import it.refill.backend.models.users.UserAuth;
import it.refill.backend.repository.users.UserAuthRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@Tag(name = "customer", description = "Getting data of single customer")
public class CustomerController {

    @Autowired
    UserAuthRepository userRepository;

    /* ############### ROLE USER ################*/  


    @Operation(summary = "Get Single Customer", description = "Get the supplier details with the ID of the supplier that made the request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "succesfull operation", content = @Content(schema = @Schema(implementation = Customer.class)))
    })
    @GetMapping("/self")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAuth> getSingleCustomer(HttpServletRequest req) {
        
        Long id = (Long) req.getAttribute("user_id");

        UserAuth prova = userRepository.getOne(id);

        return new ResponseEntity<UserAuth>(prova, HttpStatus.ACCEPTED);
    }  

    /* ############### ROLE ADMIN ################*/  

    @Operation(summary = "Get Single Customer by Id", description = "Get the supplier details with the requested Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "succesfull operation", content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping(value = "", params = {"customerId"})    
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSingleCustomerById(@RequestParam(value = "customerId") Long customerId) {

        try{
            UserAuth customer = userRepository.getOne(customerId);
            return new ResponseEntity<UserAuth>(customer, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<String>("The customer was not found", HttpStatus.NOT_FOUND );
        }

        
    }  
}