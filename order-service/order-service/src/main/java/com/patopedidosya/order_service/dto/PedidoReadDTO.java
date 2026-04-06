package com.patopedidosya.order_service.dto;

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
public class PedidoReadDTO {
    private UUID idPedido;
    private UUID idUsuario;
    private UUID idLocal;
    private String direccionEntrega;
    private String ciudadEntrega;
    private String referenciaEntrega;
    private BigDecimal costoEnvio;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private EstadoPedidoReadDTO estadoPedido;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
    private List<PedidoDetalleReadDTO> detalles;
    private List<PedidoPromocionReadDTO> promociones;
}
