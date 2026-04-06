package com.patopedidosya.payment_service.dto;

import com.patopedidosya.payment_service.model.EstadoPago;
import com.patopedidosya.payment_service.model.FormaPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoReadDTO {
    private UUID idPago;
    private UUID idPedido;
    private BigDecimal monto;
    private EstadoPago estadoPago;
    private FormaPago formaPago;
    private LocalDateTime fechaPago;
    private String referenciaTransaccion;
    private String proveedorPago;
}

