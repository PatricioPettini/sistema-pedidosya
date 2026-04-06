package com.patopedidosya.rider_service.dto;

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
public class AsignacionRiderCreateDTO {
    @NotNull(message = "El pedido es obligatorio")
    private UUID idPedido;

    @NotNull(message = "El rider es obligatorio")
    private UUID idRider;

    @NotNull(message = "El estado de asignacion es obligatorio")
    private UUID idEstadoAsignacionRider;

    @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
    private String observaciones;
}

