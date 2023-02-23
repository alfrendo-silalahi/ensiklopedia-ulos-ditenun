package com.ensiklopediaulos.ditenun.model.ensiklopedia;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ulos_colors_for_ensiklopedia")
public class UlosColorEnsiklopedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @ManyToMany(mappedBy = "colors")
    private Set<UlosEnsiklopedia> ulos = new HashSet<>();

}
