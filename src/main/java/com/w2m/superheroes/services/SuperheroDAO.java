package com.w2m.superheroes.services;

import com.w2m.superheroes.models.entities.Superhero;

import java.util.Optional;

public interface SuperheroDAO {

    Optional<Superhero> findById(Integer id);
    Superhero save(Superhero superhero);
    Iterable<Superhero> findAll();
    void deleteById(Integer id);

    Iterable<Superhero> findSuperheroesByNameContainsIgnoreCase(String name);
}
