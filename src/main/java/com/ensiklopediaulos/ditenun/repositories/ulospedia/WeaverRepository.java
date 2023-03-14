package com.ensiklopediaulos.ditenun.repositories.ulospedia;

import com.ensiklopediaulos.ditenun.models.ulospedia.Weaver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeaverRepository extends JpaRepository<Weaver, Long> {

    Optional<Weaver> findWeaverByUuid(String uuid);

}
