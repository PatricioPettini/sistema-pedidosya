package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPedidoDetalleRepository extends JpaRepository<PedidoDetalle, UUID> {
    List<PedidoDetalle> findByIdPedido(UUID idPedido);
}

