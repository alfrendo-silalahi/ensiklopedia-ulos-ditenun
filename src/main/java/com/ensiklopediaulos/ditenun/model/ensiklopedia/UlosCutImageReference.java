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
@Table(name = "ulos_cut_image_references")
public class UlosCutImageReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ulos_cut_image_reference")
    private String ulosCutImageReference;

    @Column(name = "ulos_cut_image_uri")
    private String ulosCutImageURI;

    @ManyToOne
    @JoinColumn(name = "ulos_ensiklopedia_id", nullable = false)
    private UlosEnsiklopedia ulosEnsiklopedia;

}
