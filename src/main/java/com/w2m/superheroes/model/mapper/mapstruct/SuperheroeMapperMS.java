package com.w2m.superheroes.model.mapper.mapstruct;

import com.w2m.superheroes.model.dtos.SuperheroeDTO;
import com.w2m.superheroes.model.entities.Superheroe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperheroeMapperMS {

    SuperheroeDTO mapSuperheroe(Superheroe superheroe);
}
