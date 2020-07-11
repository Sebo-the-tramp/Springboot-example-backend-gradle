package it.refill.backend.controllers.general;

import java.util.List;
import java.util.stream.Collectors;

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
import it.refill.backend.models.address.County;
import it.refill.backend.models.address.Municipality;
import it.refill.backend.models.address.Nationality;
import it.refill.backend.models.product.Category;
import it.refill.backend.payload.response.address.CountyShort;
import it.refill.backend.payload.response.address.MunicipalityShort;
import it.refill.backend.payload.response.address.NationalityShort;
import it.refill.backend.repository.address.CountyRepository;
import it.refill.backend.repository.address.MunicipalityRepository;
import it.refill.backend.repository.address.NationalityRepository;
import it.refill.backend.repository.products.CategoryRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/data")
@PreAuthorize("hasRole('USER') or hasRole('SUPPLIER') or hasRole('ADMIN') ")
public class DataController {

    @Autowired
    MunicipalityRepository municipalityRepository;

    @Autowired
    NationalityRepository nationalityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Operation(summary = "Get municipalities", description = "Get all the data of the municipalities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),        
    })
    @GetMapping("/municipalities")
    public ResponseEntity<?> getMunicipalities() {

        List<Municipality> lista = municipalityRepository.findAll();

        //to modify and delete
        List<MunicipalityShort> listNew = lista.stream()
                .map(a -> new MunicipalityShort(a.getMunicipalityId(), a.getMunicipalityName()))
                .collect(Collectors.toList());

        return new ResponseEntity<List<MunicipalityShort>>(listNew, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get counties", description = "Get all the data of the counties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),        
    })
    @GetMapping("/counties")
    public ResponseEntity<?> getCounties() {

        List<County> lista = countyRepository.findAll();

        //to modify and delete
        List<CountyShort> listNew = lista.stream()
                .map(a -> new CountyShort(a.getIdCounty(), a.getName()))
                .filter(a -> (a.getCountyId() == 92))
                .collect(Collectors.toList());

        return new ResponseEntity<List<CountyShort>>(listNew, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get nationalities", description = "Get all the data of the nationalities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),        
    })
    @GetMapping("/nationalities")
    public ResponseEntity<?> getNationalities() {

        List<Nationality> lista = nationalityRepository.findAll();

        List<NationalityShort> listNew = lista.stream()
        .map(a -> new NationalityShort(a.getNationalityId(), a.getNationalityName()))
        .collect(Collectors.toList());

        return new ResponseEntity<List<NationalityShort>>(listNew, HttpStatus.ACCEPTED);

    }    

    @Operation(summary = "Get categories", description = "Get all the data of the categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Succesfully operation"),        
    })
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){

        List<Category> lista = categoryRepository.findAll();
        return new ResponseEntity<List<Category>>(lista, HttpStatus.ACCEPTED);        
    }

}