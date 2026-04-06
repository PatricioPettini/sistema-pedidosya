package com.patopedidosya.order_service.dto;

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
public class HistorialEstadoPedidoReadDTO {
    private UUID idHistorialEstadoPedido;
    private UUID idPedido;
    private UUID idEstadoAnterior;
    private UUID idEstadoNuevo;
    private LocalDateTime fecha;
    private String detalle;
}

