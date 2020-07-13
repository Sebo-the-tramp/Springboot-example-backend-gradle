package it.refill.backend.controllers.general;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.users.User;
import it.refill.backend.payload.response.JwtResponse;
import it.refill.backend.repository.users.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")

public class UsersController {
    
    @Autowired
    UserRepository userRepository;
    
    @Operation(summary = "Get info user", description = "Get some info of the user who aasked for")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succesfully confirmed email", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "400", description = "The token is not there"),
        @ApiResponse(responseCode = "406", description = "The token is expired")
    })
    @GetMapping("/info")    
    public ResponseEntity<?> getInfoUser(HttpServletRequest req){
        
        Long id = (Long) req.getAttribute("user_id");

        try{
            User response = userRepository.getOne(id);
            return new ResponseEntity<User>(response, HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<String>("No User found", HttpStatus.INTERNAL_SERVER_ERROR);
        }     
    }

}