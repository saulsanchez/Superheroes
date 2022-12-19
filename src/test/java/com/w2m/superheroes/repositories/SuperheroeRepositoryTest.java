package com.w2m.superheroes.repositories;

import com.w2m.superheroes.model.entities.Superheroe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.w2m.superheroes.data.DummyData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuperheroeRepositoryTest {

    @Autowired
    SuperheroeRepository repository;

    @Test
    @DisplayName("Find superheroes containing a string received by parameter")
    void findSuperheroesByNameContainsIgnoreCase() {
        repository.save(batman());
        repository.save(spiderman());
        repository.save(superwoman(false));
        repository.save(superman(false));

        Iterable<Superheroe> superheroes = repository.findSuperheroesByNameContainsIgnoreCase("super");

        assertThat(((List<Superheroe>) superheroes).size() == 2).isTrue();
    }
}