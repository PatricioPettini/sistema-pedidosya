package com.patopedidosya.user_service.repository;

import com.patopedidosya.user_service.model.UserDireccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IUserDireccionRepository extends JpaRepository<UserDireccion, UUID> {
    List<UserDireccion> findByIdUsuario(UUID idUsuario);
}
