package it.refill.backend.controllers.list;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.payload.request.list.ProductPayload;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.list.ListContainsProductRepository;
import it.refill.backend.repository.list.ListProductRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/productlist")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ListContainsProductController {

    @Autowired
    ListContainsProductRepository listContainsProductRepository;

    @Autowired
    ListProductRepository productListRepository;

    @Operation(summary = "Add to some list some product", description = "Given the id of the user, of the list and of the product and the quantity it will add it to the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully added to a list", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized") })
    @PostMapping("/")
    public ResponseEntity<?> addProductToList(HttpServletRequest req, @RequestBody ProductPayload payload) {

        Long customerId = (Long) req.getAttribute("user_id");

        //only if the list is owned by the user proceed
        if (productListRepository.checkIfListIsOwnedByUser(payload.getListId(), customerId) > 0) {

            //if the product is in list
            if(listContainsProductRepository.checkIfProductIsInList(payload.getProductId(), payload.getListId()) > 0 ){
                //retrieving quantity from the database and update it
                Integer quantity = listContainsProductRepository.getQuantityProduct(payload.getListId(), payload.getProductId());

                Integer newQuantity = quantity + payload.getQuantity();

                //don't update if it is zero (double checker also in the dashboard)
                if(newQuantity >= 0){
                    listContainsProductRepository.upadteListProduct(payload.getListId(), payload.getProductId(), newQuantity);
                }                

            }else{
                if(payload.getQuantity() > 0){
                    listContainsProductRepository.addListProduct(payload.getListId(), payload.getProductId(), payload.getQuantity());
                }                
            }                    

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}