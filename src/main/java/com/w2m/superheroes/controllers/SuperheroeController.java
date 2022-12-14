package com.w2m.superheroes.controllers;

import com.w2m.superheroes.exceptions.BadRequestException;
import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/superheroes")
public class SuperheroeController {

    private final SuperheroeDAO superheroeDAO;

    @Autowired
    public SuperheroeController(SuperheroeDAO superheroeDAO) {
        this.superheroeDAO = superheroeDAO;
    }

    @GetMapping
    public List<Superheroe> getSuperheroes() {

        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findAll();

        if(superheroes.isEmpty()) {
            throw  new BadRequestException("No superheroes found");
        }

        return superheroes;
    }

    @GetMapping("/{id}")
    public Superheroe getSuperheroe (@PathVariable(value = "id") Integer id) {

        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if(!oSuperheroe.isPresent()) {
            throw new BadRequestException(String.format("The superhero with id %d does not exist", id));
        }

        return oSuperheroe.get();
    }

    @GetMapping("/search")
    public List<Superheroe> getSuperheroesByName(@RequestParam String name) {
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findSuperheroesByNameContainsIgnoreCase(name);

        if(superheroes.isEmpty()) {
            throw new BadRequestException(String.format("No superheroes with %s in their name have been found", name));
        }

        return superheroes;
    }

    @PostMapping
    public Superheroe createSuperheroe(@RequestBody Superheroe superheroe) {
        return superheroeDAO.save(superheroe);
    }

    @PutMapping("/{id}")
    public Superheroe updateSuperheroe(@PathVariable Integer id, @RequestBody Superheroe superheroe) {

        Optional<Superheroe> oSuperheroe = superheroeDAO.findById(id);

        if(!oSuperheroe.isPresent()) {
            throw new BadRequestException(String.format("The superhero with id %d does not exist", id));
        }

        Superheroe superheroeUpdate = oSuperheroe.get();
        superheroeUpdate.setName(superheroe.getName());

        return superheroeDAO.save(superheroeUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteSuperheroe(@PathVariable Integer id) {
        superheroeDAO.deleteById(id);
    }
}
