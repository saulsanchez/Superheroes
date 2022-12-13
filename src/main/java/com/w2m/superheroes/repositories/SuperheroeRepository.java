package com.w2m.superheroes.repositories;

import com.w2m.superheroes.model.entities.Superheroe;
import org.springframework.data.repository.CrudRepository;

public interface SuperheroeRepository extends CrudRepository<Superheroe, Integer> {

    Iterable<Superheroe> findSuperheroesByNameContainsIgnoreCase(String name);
}
