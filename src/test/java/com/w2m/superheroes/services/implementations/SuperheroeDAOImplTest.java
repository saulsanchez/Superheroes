package com.w2m.superheroes.services.implementations;

import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.repositories.SuperheroeRepository;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.w2m.superheroes.data.DummyData.superman;
import static com.w2m.superheroes.data.DummyData.superwoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class SuperheroeDAOImplTest {

    SuperheroeDAO superheroeDAO;
    SuperheroeRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SuperheroeRepository.class);
        superheroeDAO = new SuperheroeDAOImpl(repository);
    }

    @Test
    void findSuperheroesByNameContainsIgnoreCase() {

        //Given
        String name = "super";
        when(repository.findSuperheroesByNameContainsIgnoreCase(name))
                .thenReturn(Arrays.asList(superman(true), superwoman(true)));

        //When
        List<Superheroe> superheroes = (List<Superheroe>) superheroeDAO.findSuperheroesByNameContainsIgnoreCase(name);

        //Then
        assertThat(superheroes.get(0)).isEqualTo(superman(true));
        assertThat(superheroes.get(1)).isEqualTo(superwoman(true));

        verify(repository).findSuperheroesByNameContainsIgnoreCase(name);
    }
}