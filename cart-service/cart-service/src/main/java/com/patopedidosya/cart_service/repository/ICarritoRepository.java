package com.patopedidosya.cart_service.repository;

import com.patopedidosya.cart_service.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarritoRepository extends JpaRepository<Carrito, UUID> {
    Optional<Carrito> findByIdUsuarioAndIdLocalAndActivo(UUID idUsuario, UUID idLocal, Boolean activo);
    List<Carrito> findByIdUsuario(UUID idUsuario);
}

