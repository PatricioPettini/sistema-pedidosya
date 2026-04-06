package com.patopedidosya.payment_service.dto;

import com.patopedidosya.payment_service.model.EstadoPago;
import com.patopedidosya.payment_service.model.FormaPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoUpdateDTO {
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    private EstadoPago estadoPago;
    private FormaPago formaPago;

    @Size(max = 100, message = "La referencia de transaccion no puede superar los 100 caracteres")
    private String referenciaTransaccion;

    @Size(max = 100, message = "El proveedor de pago no puede superar los 100 caracteres")
    private String proveedorPago;
}

