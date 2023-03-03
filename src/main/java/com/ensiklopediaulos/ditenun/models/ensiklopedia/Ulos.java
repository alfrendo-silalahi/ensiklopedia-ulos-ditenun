package com.ensiklopediaulos.ditenun.models.ensiklopedia;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ulos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "ethnic")
    private String ethnic;

    @Column(name = "location")
    private String location;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "ulos_colors",
            joinColumns = {@JoinColumn(name = "ulos_id")},
            inverseJoinColumns = {@JoinColumn(name = "color_id")}
    )
    private List<Color> colors;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "technique")
    private String technique;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "func", columnDefinition = "TEXT")
    private String func;

    @Column(name = "history", columnDefinition = "TEXT")
    private String history;

    @Column(name = "available_in_ecommerce")
    private Boolean availableInEcommerce;

    @Column(name = "link_to_ecommerce")
    private String linkToEcommerce;

}
