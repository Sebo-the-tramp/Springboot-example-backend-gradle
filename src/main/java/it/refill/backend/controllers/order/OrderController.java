package it.refill.backend.controllers.order;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.order.Order;
import it.refill.backend.models.users.Customer;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.list.ListProductRepository;
import it.refill.backend.repository.order.OrderRepository;
import it.refill.backend.repository.users.CustomerRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class OrderController {

    @Autowired
    ListProductRepository productListRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;
    
    @Operation(summary = "Create new Order", description = "Given the id of the user and the list, make it an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully made the order", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/{listId}")
    public ResponseEntity<?> createOrder(HttpServletRequest req, @PathVariable("listId") Long listId){

        Long customerId = (Long) req.getAttribute("user_id");

        //check if the list is owned by the customer
        if(productListRepository.checkIfListIsOwnedByUser(listId, customerId) > 0){

            Customer customer = customerRepository.getOne(customerId);

            Order newOrder = new Order(new Date(), false, "some detail", false, customer);

            Order inserted = orderRepository.saveAndFlush(newOrder);

            orderRepository.copyProducts(inserted.getId());

            return new ResponseEntity<Long>(inserted.getId(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //create the new order

        //copy the products from the list to the order        
    }

}