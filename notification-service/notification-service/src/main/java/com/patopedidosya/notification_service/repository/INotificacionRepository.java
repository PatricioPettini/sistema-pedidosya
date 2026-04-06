package com.patopedidosya.notification_service.repository;

import com.patopedidosya.notification_service.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface INotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByIdUsuario(UUID idUsuario);
}
