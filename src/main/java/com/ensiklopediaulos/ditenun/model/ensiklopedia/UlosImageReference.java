package com.ensiklopediaulos.ditenun.model.ensiklopedia;

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
@Table(name = "ulos_image_references")
public class UlosImageReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ulos_image_reference")
    private String ulosImageReference;

    @Column(name = "ulos_image_uri")
    private String ulosImageURI;

    @ManyToOne
    @JoinColumn(name = "ulos_ensiklopedia_id", nullable = false)
    private UlosEnsiklopedia ulosEnsiklopedia;

}
