package it.refill.backend.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.users.Customer;
import it.refill.backend.repository.users.UserAuthRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    @Autowired
    UserAuthRepository userRepository;

    /* ############### ROLE ADMIN ################*/  

    @Operation(summary = "Get All customers", description = "Get All the customers in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "succesfull operation", content = @Content(array= @ArraySchema(schema = @Schema(implementation = Customer.class)))),        
    })
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCustomers() {                              
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.ACCEPTED);
    }  
}