package com.patopedidosya.payment_service.repository;

import com.patopedidosya.payment_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPagoRepository extends JpaRepository<Pago, UUID> {
    Optional<Pago> findByIdPedido(UUID idPedido);
}

