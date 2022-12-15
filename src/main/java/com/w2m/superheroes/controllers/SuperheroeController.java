package com.w2m.superheroes.controllers;

import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/superheroes")
public class SuperheroeController {

    private final SuperheroeDAO superheroeDAO;

    @Autowired
    public SuperheroeController(SuperheroeDAO superheroeDAO) {
        this.superheroeDAO = superheroeDAO;
    }

    @GetMapping
    public ResponseEntity<?> getSuperheroes() {

        Map<String, Object> response = new HashMap<>();
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findAll();

        if(superheroes.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", "No superheroes in the database");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", Boolean.TRUE);
        response.put("data", superheroes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSuperheroe (@PathVariable(value = "id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if(!oSuperheroe.isPresent()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("The superhero with id %d does not exist", id));
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", Boolean.TRUE);
        response.put("data", oSuperheroe.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSuperheroesByName(@RequestParam String name) {

        Map<String, Object> response = new HashMap<>();
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findSuperheroesByNameContainsIgnoreCase(name);

        if(superheroes.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("No superheroes with %s in their name have been found", name));
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", Boolean.TRUE);
        response.put("data", superheroes);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createSuperheroe(@Valid @RequestBody Superheroe superheroe, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put("success", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", Boolean.TRUE);
        response.put("data", superheroeDAO.save(superheroe));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSuperheroe(@PathVariable Integer id, @Valid @RequestBody Superheroe superheroe, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                response.put(error.getField(), error.getDefaultMessage());
            });
            response.put("success", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

        if(!oSuperheroe.isPresent()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("The superhero with id %d does not exist", id));
            return ResponseEntity.badRequest().body(response);
        }

        Superheroe superheroeUpdate = oSuperheroe.get();
        superheroeUpdate.setName(superheroe.getName());

        response.put("success", Boolean.TRUE);
        response.put("data", superheroeDAO.save(superheroeUpdate));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSuperheroe(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();

        superheroeDAO.deleteById(id);
        response.put("success", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }
}
