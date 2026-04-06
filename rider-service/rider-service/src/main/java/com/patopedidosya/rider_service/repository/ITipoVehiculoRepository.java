package com.patopedidosya.rider_service.repository;

import com.patopedidosya.rider_service.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ITipoVehiculoRepository extends JpaRepository<TipoVehiculo, UUID> {
    Optional<TipoVehiculo> findByNombre(String nombre);
}
