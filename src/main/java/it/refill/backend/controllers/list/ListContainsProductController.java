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
import it.refill.backend.payload.request.list.ProductList;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.list.ListContainsProductRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/productlist")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ListContainsProductController {

    @Autowired
    ListContainsProductRepository listContainsProductRepository;

    @Operation(summary = "Add to some list some product", description = "Given the id of the user, of the list and of the product and the quantity it will add it to the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully added to a list", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/")
    public ResponseEntity<?> addProductToList(HttpServletRequest req, @RequestBody ProductList payload) {

        Long customerId = (Long) req.getAttribute("user_id");

        // if the product is already in the list just update it's value otherwise add it
        // to the list
        try {
            Integer quantity = listContainsProductRepository.checkIfProductIsInList(payload.getProductId(),
                    payload.getListId());

            // if the quantity of some products is positive update it otherwise remove the
            // item from the list
            if (quantity + payload.getQuantity() > 0) {
                listContainsProductRepository.upadteProductList(payload.getListId(), payload.getProductId(), customerId,
                        (quantity + payload.getQuantity()));
            } else {
                listContainsProductRepository.removeProductList(payload.getListId(), payload.getProductId(),
                        customerId);
            }

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } catch (Exception e) {
            listContainsProductRepository.addProductList(payload.getListId(), payload.getProductId(), customerId,
                    payload.getQuantity());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

}