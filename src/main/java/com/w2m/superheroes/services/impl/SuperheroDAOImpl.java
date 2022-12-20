package com.w2m.superheroes.services.impl;

import com.w2m.superheroes.models.entities.Superhero;
import com.w2m.superheroes.repositories.SuperheroRepository;
import com.w2m.superheroes.services.SuperheroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SuperheroDAOImpl implements SuperheroDAO {

    @Autowired
    private SuperheroRepository repository;

    public SuperheroDAOImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Superhero> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Superhero save(Superhero superhero) {
        return repository.save(superhero);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Superhero> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Superhero> findSuperheroesByNameContainsIgnoreCase(String name) {
        return repository.findSuperheroesByNameContainsIgnoreCase(name);
    }


}
