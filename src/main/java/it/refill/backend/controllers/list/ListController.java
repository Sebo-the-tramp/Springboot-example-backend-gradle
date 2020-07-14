package it.refill.backend.controllers.list;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.list.ProductList;
import it.refill.backend.payload.request.list.ListPayload;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.list.ProductListRepository;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/list")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ListController {

    @Autowired
    ProductListRepository productListRepository;

    @Operation(summary = "Create new list to User", description = "Given the Id of a user and a name of the list, create one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully added a list", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/")
    public ResponseEntity<?> createListToUser(HttpServletRequest req, @RequestBody ListPayload payloadList) {

        try {
            Long id = (Long) req.getAttribute("user_id");

            productListRepository.addProductList(payloadList.getName(), payloadList.getDescription(),
                    payloadList.getIsCart(), id);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete list of User", description = "Given the id of a list if it is of the user, delete it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully deleted a list", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "404", description = "The id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteListToUser(HttpServletRequest req, @PathVariable("listId") Long idList) {
        
            Long id = (Long) req.getAttribute("user_id");            

            Integer test = productListRepository.deleteList(idList, id);

            if(test > 0){
                return new ResponseEntity<>(HttpStatus.ACCEPTED);     
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @Operation(summary = "Get list user", description = "get All the list of some user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully retrieved lists", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping(value="/")
    public ResponseEntity<List<ProductList>> getAllListUser(HttpServletRequest req) {                           

        Long id = (Long) req.getAttribute("user_id");            

        List<ProductList> result = productListRepository.getAllListsUser(id);

        return new ResponseEntity<List<ProductList>>(result, HttpStatus.ACCEPTED);
    }    

}