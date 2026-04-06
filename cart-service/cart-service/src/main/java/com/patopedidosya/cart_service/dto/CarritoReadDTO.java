package com.patopedidosya.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoReadDTO {
    private UUID idCarrito;
    private UUID idUsuario;
    private UUID idLocal;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<CarritoDetalleReadDTO> detalles;
    private List<CarritoPromocionReadDTO> promociones;
}

