package com.patopedidosya.notification_service.dto;

import com.patopedidosya.notification_service.model.CanalNotificacion;
import com.patopedidosya.notification_service.model.EstadoNotificacion;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionUpdateDTO {
    @Size(max = 150, message = "El titulo no puede superar los 150 caracteres")
    private String titulo;

    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    private CanalNotificacion canal;
    private EstadoNotificacion estado;
}

