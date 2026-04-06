package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IEstadoPedidoRepository extends JpaRepository<EstadoPedido, UUID> {
    Optional<EstadoPedido> findByNombre(String nombre);
}
