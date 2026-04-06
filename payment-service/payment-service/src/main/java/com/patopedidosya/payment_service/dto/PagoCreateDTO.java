package com.patopedidosya.payment_service.dto;

import com.patopedidosya.payment_service.model.EstadoPago;
import com.patopedidosya.payment_service.model.FormaPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PagoCreateDTO {
    @NotNull(message = "El pedido es obligatorio")
    private UUID idPedido;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El estado de pago es obligatorio")
    private EstadoPago estadoPago;

    @NotNull(message = "La forma de pago es obligatoria")
    private FormaPago formaPago;

    @Size(max = 100, message = "La referencia de transaccion no puede superar los 100 caracteres")
    private String referenciaTransaccion;

    @Size(max = 100, message = "El proveedor de pago no puede superar los 100 caracteres")
    private String proveedorPago;
}

