package com.patopedidosya.direccion_service.repository;

import com.patopedidosya.direccion_service.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDireccionRepository extends JpaRepository<Direccion, UUID> {
}
