package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.RubroLocal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRubroLocalRepository extends JpaRepository<RubroLocal, UUID> {
    Optional<RubroLocal> findByNombre(String nombre);
}
