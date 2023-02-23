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
@Table(name = "ulos_motive_image_references")
public class UlosMotiveImageReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ulos_motive_image_reference")
    private String ulosMotiveImageReference;

    @Column(name = "ulos_motive_image_uri")
    private String ulosMotiveImageURI;

    @ManyToOne
    @JoinColumn(name = "ulos_ensiklopedia_id", nullable = false)
    private UlosEnsiklopedia ulosEnsiklopedia;

}
