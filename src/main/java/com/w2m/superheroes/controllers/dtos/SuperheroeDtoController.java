package com.w2m.superheroes.controllers.dtos;

import com.w2m.superheroes.model.dtos.SuperheroeDTO;
import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.model.mapper.mapstruct.SuperheroeMapperMS;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
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
public class SuperheroeDtoController {

    Logger logger = LoggerFactory.getLogger(SuperheroeDtoController.class);

    @Autowired
    private SuperheroeDAO superheroeDAO;

    @Autowired
    SuperheroeMapperMS mapper;

    @GetMapping
    @ApiOperation(value = "Search all superheroes")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SUPERHEROES_NOT_FOUND, response = SuperheroeDTO.class),
            @ApiResponse(code = 404, message = Constants.SUPERHEROES_NOT_FOUND)
    })
    public ResponseEntity<?> getSuperheroes() {

        Map<String, Object> response = new HashMap<>();
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findAll();

        if(superheroes.isEmpty()) {
            logger.error(Constants.SUPERHEROES_NOT_FOUND);
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.DATA, Constants.SUPERHEROES_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<SuperheroeDTO> superheroeDTOS = superheroes
                .stream()
                .map(mapper::mapSuperheroe)
                .collect(Collectors.toList());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroeDTOS);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search superhero by code")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SATISFACTORILY_EXECUTED, response = SuperheroeDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> getSuperheroe (@PathVariable(value = "id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if(!oSuperheroe.isPresent()) {
            logger.error(String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        SuperheroeDTO superheroeDTO = mapper.mapSuperheroe(oSuperheroe.get());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroeDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search superhero by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SATISFACTORILY_EXECUTED, response = SuperheroeDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given name have been found in the database")
    })
    public ResponseEntity<?> getSuperheroesByName(@RequestParam String name) {

        Map<String, Object> response = new HashMap<>();
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findSuperheroesByNameContainsIgnoreCase(name);

        if(superheroes.isEmpty()) {
            logger.error(String.format(Constants.SUPERHERO_NAME_NOT_FOUND, name));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_NAME_NOT_FOUND, name));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<SuperheroeDTO> superheroeDTOS = superheroes
                .stream()
                .map(mapper::mapSuperheroe)
                .collect(Collectors.toList());

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroeDTOS);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation(value = "Create superhero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Superhero created")
    })
    public ResponseEntity<?> createSuperheroe(@Valid @RequestBody Superheroe superheroe, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                logger.error(error.getDefaultMessage());
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put(Constants.SUCCESS, Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        SuperheroeDTO superheroeDTO = mapper.mapSuperheroe(superheroeDAO.save(superheroe));

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update superhero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Superhero updated successfully", response = SuperheroeDTO.class),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> updateSuperheroe(@PathVariable Integer id, @Valid @RequestBody Superheroe superheroe, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                logger.error(error.getDefaultMessage());
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put("success", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        if(!oSuperheroe.isPresent()) {
            logger.error(String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            response.put(Constants.SUCCESS, Boolean.FALSE);
            response.put(Constants.MESSAGE, String.format(Constants.SUPERHERO_ID_NOT_FOUND, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Superheroe superheroeUpdate = oSuperheroe.get();
        superheroeUpdate.setName(superheroe.getName());

        SuperheroeDTO superheroeDTO = mapper.mapSuperheroe(superheroeDAO.save(superheroeUpdate));

        response.put(Constants.SUCCESS, Boolean.TRUE);
        response.put(Constants.DATA, superheroeDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete superhero")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Superhero deleted successfully"),
            @ApiResponse(code = 404, message = "No superheroes with the given id have been found in the database")
    })
    public ResponseEntity<?> deleteSuperheroe(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();

        ResponseEntity<?> getSuperheroeResponseEntity = getSuperheroe(id);

        if (getSuperheroeResponseEntity.getStatusCode().toString().equals(HttpStatus.NOT_FOUND.toString())) {
            return getSuperheroeResponseEntity;
        }

        superheroeDAO.deleteById(id);
        response.put(Constants.SUCCESS, Boolean.TRUE);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
