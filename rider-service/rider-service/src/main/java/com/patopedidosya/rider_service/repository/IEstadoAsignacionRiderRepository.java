package com.patopedidosya.rider_service.repository;

import com.patopedidosya.rider_service.model.EstadoAsignacionRider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IEstadoAsignacionRiderRepository extends JpaRepository<EstadoAsignacionRider, UUID> {
    Optional<EstadoAsignacionRider> findByNombre(String nombre);
}
