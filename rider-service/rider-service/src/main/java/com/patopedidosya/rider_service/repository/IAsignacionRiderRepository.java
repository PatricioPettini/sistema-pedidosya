package com.patopedidosya.rider_service.repository;

import com.patopedidosya.rider_service.model.AsignacionRider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAsignacionRiderRepository extends JpaRepository<AsignacionRider, UUID> {
    List<AsignacionRider> findByIdPedido(UUID idPedido);
    List<AsignacionRider> findByIdRider(UUID idRider);
}

