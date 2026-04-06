package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.model.PedidoPromocion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPedidoPromocionRepository extends JpaRepository<PedidoPromocion, UUID> {
    List<PedidoPromocion> findByIdPedido(UUID idPedido);
}
