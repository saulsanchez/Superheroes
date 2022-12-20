package com.w2m.superheroes.repositories;

import com.w2m.superheroes.models.entities.Superhero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.w2m.superheroes.data.DummyData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SuperheroRepositoryTest {

    @Autowired
    SuperheroRepository repository;

    @Test
    @DisplayName("Find superheroes containing a string received by parameter")
    void findSuperheroesByNameContainsIgnoreCase() {
        repository.save(batman());
        repository.save(spiderman());
        repository.save(superwoman(false));
        repository.save(superman(false));

        Iterable<Superhero> superheroes = repository.findSuperheroesByNameContainsIgnoreCase("super");

        assertThat(((List<Superhero>) superheroes).size() == 2).isTrue();
    }
}