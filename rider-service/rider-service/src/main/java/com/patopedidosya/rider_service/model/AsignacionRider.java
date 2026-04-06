package com.patopedidosya.rider_service.model;

import jakarta.persistence.Entity;
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
public class AsignacionRider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idAsignacionRider;

    private UUID idPedido;
    private UUID idRider;
    private UUID idEstadoAsignacionRider;
    private LocalDateTime fechaAsignacion;
    private String observaciones;
}

