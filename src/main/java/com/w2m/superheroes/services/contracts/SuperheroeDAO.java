package com.w2m.superheroes.services.contracts;

import com.w2m.superheroes.model.entities.Superheroe;

import java.util.Optional;

public interface SuperheroeDAO {

    Optional<Superheroe> findById(Integer id);
    Superheroe save(Superheroe superheroe);
    Iterable<Superheroe> findAll();
    void deleteById(Integer id);

    Iterable<Superheroe> findSuperheroesByNameContainsIgnoreCase(String name);
}
