package com.w2m.superheroes.services.implementations;

import com.w2m.superheroes.models.entities.Superhero;
import com.w2m.superheroes.repositories.SuperheroRepository;
import com.w2m.superheroes.services.SuperheroDAO;
import com.w2m.superheroes.services.impl.SuperheroDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.w2m.superheroes.data.DummyData.superman;
import static com.w2m.superheroes.data.DummyData.superwoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class SuperheroDAOImplTest {

    SuperheroDAO superheroeDAO;
    SuperheroRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SuperheroRepository.class);
        superheroeDAO = new SuperheroDAOImpl(repository);
    }

    @Test
    void findSuperheroesByNameContainsIgnoreCase() {

        //Given
        String name = "super";
        when(repository.findSuperheroesByNameContainsIgnoreCase(name))
                .thenReturn(Arrays.asList(superman(true), superwoman(true)));

        //When
        List<Superhero> superheroes = (List<Superhero>) superheroeDAO.findSuperheroesByNameContainsIgnoreCase(name);

        //Then
        assertThat(superheroes.get(0)).isEqualTo(superman(true));
        assertThat(superheroes.get(1)).isEqualTo(superwoman(true));

        verify(repository).findSuperheroesByNameContainsIgnoreCase(name);
    }
}