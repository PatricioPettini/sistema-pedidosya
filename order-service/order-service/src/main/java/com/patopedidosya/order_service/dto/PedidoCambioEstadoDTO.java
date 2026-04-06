package com.patopedidosya.order_service.dto;

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
public class PedidoCambioEstadoDTO {
    @NotNull(message = "El nuevo estado es obligatorio")
    private UUID idEstadoPedido;

    @Size(max = 255, message = "El detalle no puede superar los 255 caracteres")
    private String detalle;
}

