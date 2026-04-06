package com.patopedidosya.rider_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoAsignacionRiderReadDTO {
    private UUID idEstadoAsignacionRider;
    private String nombre;
    private String descripcion;
    private Integer orden;
}

