package com.w2m.superheroes.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Superhero data", value = "SuperheroDTO", reference = "SuperheroDTO")
public class SuperheroDTO {

    @ApiModelProperty(name = "System code", example = "1")
    private Integer id;
    @NotNull
    @NotEmpty
    @ApiModelProperty(name = "Superhero name", example = "Superman", required = true)
    private String name;
}
