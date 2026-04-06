package com.patopedidosya.notification_service.dto;

import com.patopedidosya.notification_service.model.CanalNotificacion;
import com.patopedidosya.notification_service.model.EstadoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionCreateDTO {
    @NotNull(message = "El usuario es obligatorio")
    private UUID idUsuario;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 150, message = "El titulo no puede superar los 150 caracteres")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @NotNull(message = "El canal es obligatorio")
    private CanalNotificacion canal;

    @NotNull(message = "El estado es obligatorio")
    private EstadoNotificacion estado;
}

