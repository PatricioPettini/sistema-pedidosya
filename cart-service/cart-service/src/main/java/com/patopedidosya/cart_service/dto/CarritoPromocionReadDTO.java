package com.patopedidosya.cart_service.dto;

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
public class CarritoPromocionReadDTO {
    private UUID idCarritoPromocion;
    private UUID idCarrito;
    private UUID idPromocion;
    private BigDecimal montoAplicado;
}

