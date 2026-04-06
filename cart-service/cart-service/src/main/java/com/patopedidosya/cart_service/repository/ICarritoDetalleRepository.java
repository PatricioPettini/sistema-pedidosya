package com.patopedidosya.cart_service.repository;

import com.patopedidosya.cart_service.model.CarritoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarritoDetalleRepository extends JpaRepository<CarritoDetalle, UUID> {
    List<CarritoDetalle> findByIdCarrito(UUID idCarrito);
    Optional<CarritoDetalle> findByIdCarritoAndIdItem(UUID idCarrito, UUID idItem);
}

