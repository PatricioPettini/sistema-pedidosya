package com.patopedidosya.rider_service.dto;

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
public class AsignacionRiderReadDTO {
    private UUID idAsignacionRider;
    private UUID idPedido;
    private RiderReadDTO rider;
    private EstadoAsignacionRiderReadDTO estadoAsignacionRider;
    private LocalDateTime fechaAsignacion;
    private String observaciones;
}

