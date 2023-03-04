package com.ensiklopediaulos.ditenun.repositories;

import com.ensiklopediaulos.ditenun.models.ulospedia.Ulos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UlosRepository extends JpaRepository<Ulos, Long> {

    Optional<Ulos> findByUuid(String uuid);

}
