package com.ensiklopediaulos.ditenun.repositories.ulospedia;

import com.ensiklopediaulos.ditenun.models.ulospedia.Ulos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UlosRepository extends JpaRepository<Ulos, Long> {

    /**
     * find ulos by uuid from db
     */
    Optional<Ulos> findByUuid(String uuid);

}
