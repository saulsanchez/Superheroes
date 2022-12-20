package com.w2m.superheroes.repositories;

import com.w2m.superheroes.models.entities.Superhero;
import org.springframework.data.repository.CrudRepository;

public interface SuperheroRepository extends CrudRepository<Superhero, Integer> {

    Iterable<Superhero> findSuperheroesByNameContainsIgnoreCase(String name);
}
