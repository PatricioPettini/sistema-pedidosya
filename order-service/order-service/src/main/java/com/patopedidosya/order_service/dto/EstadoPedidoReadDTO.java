package com.patopedidosya.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedidoReadDTO {
    private UUID idEstadoPedido;
    private String nombre;
    private String descripcion;
    private Integer orden;
    private Boolean esFinal;
}

