package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByIdUsuario(UUID idUsuario);
    List<Pedido> findByIdLocal(UUID idLocal);
}

