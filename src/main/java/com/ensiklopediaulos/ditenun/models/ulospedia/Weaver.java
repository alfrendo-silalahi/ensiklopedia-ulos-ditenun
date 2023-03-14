package com.ensiklopediaulos.ditenun.models.ulospedia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weavers")
public class Weaver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ethnic")
    private String ethnic;

    @Column(name = "domicile")
    private String domicile;

    @Column(name = "the_loom")
    private String theLoom;

    @Column(name = "technique")
    private String technique;

    @Column(name = "story", columnDefinition = "TEXT")
    private String story;

    @Column(name = "image_reference")
    private String imageReference;

}
