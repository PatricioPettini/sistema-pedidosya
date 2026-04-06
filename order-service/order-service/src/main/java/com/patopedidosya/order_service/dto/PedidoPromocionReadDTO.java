package com.patopedidosya.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPromocionReadDTO {
    private UUID idPedidoPromocion;
    private UUID idPedido;
    private UUID idPromocion;
    private BigDecimal montoAplicado;
}

