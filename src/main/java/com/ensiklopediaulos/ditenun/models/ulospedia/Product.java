package com.ensiklopediaulos.ditenun.models.ulospedia;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_reference")
    private String imageReference;

    @Column(name = "name")
    private String name;

    @Column(name = "link_to_ecommerce")
    private String linkToEcommerce;

    @ManyToOne
    @JoinColumn(name = "ulos_id")
    private Ulos ulos;

}
