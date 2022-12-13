package com.w2m.superheroes.services.implementations;

import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.repositories.SuperheroeRepository;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SuperheroeDAOImpl implements SuperheroeDAO {

    @Autowired
    private SuperheroeRepository repository;

    public SuperheroeDAOImpl(SuperheroeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Superheroe> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Superheroe save(Superheroe superheroe) {
        return repository.save(superheroe);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Superheroe> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Superheroe> findSuperheroesByNameContainsIgnoreCase(String name) {
        return repository.findSuperheroesByNameContainsIgnoreCase(name);
    }


}
