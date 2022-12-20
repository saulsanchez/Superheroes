package com.w2m.superheroes.models.mappers;

import com.w2m.superheroes.models.dtos.SuperheroDTO;
import com.w2m.superheroes.models.entities.Superhero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperheroMapper {

    SuperheroDTO mapSuperhero(Superhero superhero);
}
