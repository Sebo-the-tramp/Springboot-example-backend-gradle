package it.refill.backend.controllers.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.users.Supplier;
import it.refill.backend.repository.users.SupplierRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/suppliers")
public class SuppliersController {

    @Autowired
    SupplierRepository supplierRepository;    
   
    /* ############### ROLE ADMIN ################*/


    @Operation(summary = "Get supplier", description = "Get all suppliers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                        
    })
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllSuppliers() {

        List<Supplier> supplierList = supplierRepository.findAll();

        return new ResponseEntity<List<Supplier>>(supplierList, HttpStatus.ACCEPTED);
        
    }

}