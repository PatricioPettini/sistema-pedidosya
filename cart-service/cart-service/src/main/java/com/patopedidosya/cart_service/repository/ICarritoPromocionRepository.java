package com.patopedidosya.cart_service.repository;

import com.patopedidosya.cart_service.model.CarritoPromocion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICarritoPromocionRepository extends JpaRepository<CarritoPromocion, UUID> {
    List<CarritoPromocion> findByIdCarrito(UUID idCarrito);
}

