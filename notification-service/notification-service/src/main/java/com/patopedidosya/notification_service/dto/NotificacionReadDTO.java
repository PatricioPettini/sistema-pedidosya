package com.patopedidosya.notification_service.dto;

import com.patopedidosya.notification_service.model.CanalNotificacion;
import com.patopedidosya.notification_service.model.EstadoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionReadDTO {
    private UUID idNotificacion;
    private UUID idUsuario;
    private String titulo;
    private String mensaje;
    private CanalNotificacion canal;
    private EstadoNotificacion estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
}

