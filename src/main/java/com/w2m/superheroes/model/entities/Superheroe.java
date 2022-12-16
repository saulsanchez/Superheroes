package com.w2m.superheroes.model.entities;

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
public class Superheroe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    public Superheroe(Integer id, String name) {
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
        Superheroe that = (Superheroe) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
