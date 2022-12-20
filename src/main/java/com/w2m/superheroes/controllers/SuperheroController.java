package com.w2m.superheroes.controllers;

import com.w2m.superheroes.models.dtos.SuperheroDTO;
import com.w2m.superheroes.models.entities.Superhero;
import com.w2m.superheroes.models.mappers.SuperheroMapper;
import com.w2m.superheroes.services.SuperheroDAO;
import com.w2m.superheroes.utils.Constants;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/superheroes")
@Api(value = "Actions related to superheroes", tags = "Actions on superheroes")
public class SuperheroController {

    Logger logger = LoggerFactory.getLogger(SuperheroController.class);

    @Autowired
    private SuperheroDAO superheroDAO;

    @Autowired
    SuperheroMapper mapper;

    @GetMapping
    @ApiOperation(value = "Search all superheroes")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SUPERHEROES_NOT_FOUND, response = SuperheroDTO.class),
            @ApiResponse(code = 404, message = Constants.SUPERHEROES_NOT_FOUND)
    })
    public ResponseEntity<?> getSuperheroes() {

        Map<String, Object> response = new HashMap<>();
        List<Superhero> superheroes = (List<Superhero>) superheroDAO.findAll();

        if(superheroes.isEmpty()) {
            logger.error(Constants.SUPERHEROES_NOT_FOUND);
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.DATA, Constants.SUPERHEROES_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<SuperheroDTO> superheroDTOS = superheroes
                .stream()
                .map(mapper::mapSuperhero)
                .collect(Collectors.toList());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroDTOS);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search superhero by code")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SATISFACTORILY_EXECUTED, response = SuperheroDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> getSuperhero(@PathVariable(value = "id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superhero> oSuperhero = superheroDAO.findById(id);

        if(!oSuperhero.isPresent()) {
            logger.error(String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        SuperheroDTO superheroDTO = mapper.mapSuperhero(oSuperhero.get());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search superhero by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SATISFACTORILY_EXECUTED, response = SuperheroDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given name have been found in the database")
    })
    public ResponseEntity<?> getSuperheroesByName(@RequestParam String name) {

        Map<String, Object> response = new HashMap<>();
        List<Superhero> superheroes = (List<Superhero>) superheroDAO.findSuperheroesByNameContainsIgnoreCase(name);

        if(superheroes.isEmpty()) {
            logger.error(String.format(Constants.SUPERHERO_NAME_NOT_FOUND, name));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_NAME_NOT_FOUND, name));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<SuperheroDTO> superheroDTOS = superheroes
                .stream()
                .map(mapper::mapSuperhero)
                .collect(Collectors.toList());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroDTOS);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation(value = "Create superhero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Superhero created")
    })
    public ResponseEntity<?> createSuperhero(@Valid @RequestBody Superhero superhero, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                logger.error(error.getDefaultMessage());
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put(Constants.SUCCESS, Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        SuperheroDTO superheroDTO = mapper.mapSuperhero(superheroDAO.save(superhero));

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update superhero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Superhero updated successfully", response = SuperheroDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> updateSuperhero(@PathVariable Integer id, @Valid @RequestBody Superhero superhero, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superhero> oSuperhero = superheroDAO.findById(id);

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                logger.error(error.getDefaultMessage());
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put("success", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        if(!oSuperhero.isPresent()) {
            logger.error(String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Superhero superheroUpdate = oSuperhero.get();
        superheroUpdate.setName(superhero.getName());

        SuperheroDTO superheroDTO = mapper.mapSuperhero(superheroDAO.save(superheroUpdate));

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete superhero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Superhero deleted successfully"),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> deleteSuperhero(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();

        ResponseEntity<?> getSuperheroeResponseEntity = getSuperhero(id);

        if (getSuperheroeResponseEntity.getStatusCode().toString().equals(HttpStatus.NOT_FOUND.toString())) {
            return getSuperheroeResponseEntity;
        }

        superheroDAO.deleteById(id);
        response.put(Constants.SUCCESS, Boolean.TRUE);

        return ResponseEntity.ok().body(response);
    }
}
