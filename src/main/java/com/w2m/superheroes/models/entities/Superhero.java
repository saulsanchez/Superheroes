package com.w2m.superheroes.models.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "superheroes")
@ApiModel(description = "Superhero data", value = "Superhero", reference = "Superhero")
public class Superhero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(name = "Superhero name", example = "Superman", required = true)
    private String name;
    @Column(name = "entry_date")
    @ApiModelProperty(hidden = true)
    private LocalDateTime entryDate;
    @Column(name = "modification_date")
    @ApiModelProperty(hidden = true)
    private LocalDateTime modificationDate;

    public Superhero(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @PrePersist
    private void beforePersist() {
        this.entryDate = LocalDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        this.modificationDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhero that = (Superhero) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
