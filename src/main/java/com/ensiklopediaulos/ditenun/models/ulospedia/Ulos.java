package com.ensiklopediaulos.ditenun.models.ulospedia;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "func", columnDefinition = "TEXT")
    private String func;

    @OneToMany(mappedBy = "ulos")
    private List<Product> products = new ArrayList<>();

    @Column(name = "main_image_reference")
    private String mainImageReference;

//    @Column(name = "available_in_ecommerce")
//    private Boolean availableInEcommerce;
//
//    @Column(name = "link_to_ecommerce")
//    private String linkToEcommerce;

}
