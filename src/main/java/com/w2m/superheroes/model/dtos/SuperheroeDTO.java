package com.w2m.superheroes.model.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuperheroeDTO {

    private Integer id;
    @NotNull
    @NotEmpty
    private String name;
}
