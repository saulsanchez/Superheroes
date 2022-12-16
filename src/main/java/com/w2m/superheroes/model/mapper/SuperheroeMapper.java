package com.w2m.superheroes.model.mapper;

import com.w2m.superheroes.model.dtos.SuperheroeDTO;
import com.w2m.superheroes.model.entities.Superheroe;

@Deprecated
public class SuperheroeMapper {

    public static SuperheroeDTO mapSuperheroe(Superheroe superheroe) {

        SuperheroeDTO dto = new SuperheroeDTO();
        dto.setId(superheroe.getId());
        dto.setName(superheroe.getName());

        return dto;
    }

    public static Superheroe mapSuperheroeDTO(SuperheroeDTO dto) {

        Superheroe superheroe = new Superheroe();
        superheroe.setId(dto.getId());
        superheroe.setName(dto.getName());

        return superheroe;
    }
}
