package it.refill.backend.controllers.product;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.views.View;
import it.refill.backend.repository.products.ProductRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;    


    /* ############### ROLE USER ################*/

    @Operation(summary = "Get all products in the database", description = "Get all products from the database in general")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                        
    })
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.ACCEPTED);
    }    

    /* ############### ROLE SUPPLIER ################*/

    @Operation(summary = "Get all products for some supplier", description = "Get all products from the database from the supplier who asked them")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),                        
    })    
    @JsonView(View.Supplier.class)
    @GetMapping(value = "/supplier")
    @PreAuthorize("hasRole('SUPPLIER')")    
    public ResponseEntity<?> getProductsSupplier(HttpServletRequest request){

        Long user_id = (Long) request.getAttribute("user_id");              

        return new ResponseEntity<>(productRepository.findBySupplierId(user_id), HttpStatus.ACCEPTED);
    }

    /* ############### ROLE ADMIN ################*/    

    @Operation(summary = "Get all products from some supplier", description = "Get all products from the database from a requested supplier id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),            
        @ApiResponse(responseCode = "404", description = "Supplier not found")                        
    })
    @JsonView(View.Supplier.class)
    @GetMapping(value = "/single_supplier", params = {"supplierId"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProductsSupplierById(@RequestParam(value="supplierId") Long supplierId){          

        try {
            return new ResponseEntity<>(productRepository.findBySupplierId(supplierId), HttpStatus.ACCEPTED);   
        } catch (Exception e) {
            return new ResponseEntity<>("The supplier id wasn't there", HttpStatus.NOT_FOUND);
        }                
    } 
   
}