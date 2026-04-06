package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.model.HistorialEstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IHistorialEstadoPedidoRepository extends JpaRepository<HistorialEstadoPedido, UUID> {
    List<HistorialEstadoPedido> findByIdPedido(UUID idPedido);
}

