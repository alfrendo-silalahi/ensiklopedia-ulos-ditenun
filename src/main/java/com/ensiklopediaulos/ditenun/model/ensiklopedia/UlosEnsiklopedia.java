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
@Table(name = "ulos_ensiklopedia")
public class UlosEnsiklopedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ethnic")
    private String ethnic;

    @Column(name = "location")
    private String location;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ulos_colors_ensiklopedia",
            joinColumns = { @JoinColumn(name = "ulos_id") },
            inverseJoinColumns = { @JoinColumn(name = "color_id") }
    )
    private Set<UlosColorEnsiklopedia> colors = new HashSet<>();

    // === SIZE ===
    @Column(name = "length")
    private Integer length;
    @Column(name = "width")
    private Integer width;
    // === SIZE ===

    @Column(name = "technique")
    private String technique;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "func", columnDefinition = "TEXT")
    private String func;

    @Column(name = "history", columnDefinition = "TEXT")
    private String history;

    @Column(name = "ecommerce_link")
    private String ecommerceLink;

    // ulos photos (main photo + other photos)
    @Column(name = "main_image_reference")
    private String mainImageReference;

    @Column(name = "main_image_uri")
    private String mainImageURI;

    @OneToMany(mappedBy = "ulosEnsiklopedia")
    private Set<UlosImageReference> ulosImageReference = new HashSet<>();

    // potongan ulos
    @OneToMany(mappedBy = "ulosEnsiklopedia")
    private Set<UlosCutImageReference> ulosCutImageReference = new HashSet<>();

    // motif ulos
    @OneToMany(mappedBy = "ulosEnsiklopedia")
    private Set<UlosMotiveImageReference> ulosMotiveImageReference = new HashSet<>();

}
