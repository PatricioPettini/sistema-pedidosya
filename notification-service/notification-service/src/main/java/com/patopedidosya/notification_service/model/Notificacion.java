package com.patopedidosya.notification_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idNotificacion;

    private UUID idUsuario;
    private String titulo;
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private CanalNotificacion canal;

    @Enumerated(EnumType.STRING)
    private EstadoNotificacion estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
}

