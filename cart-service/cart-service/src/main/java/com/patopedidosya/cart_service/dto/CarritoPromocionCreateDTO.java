package com.patopedidosya.cart_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class CarritoPromocionCreateDTO {
    @NotNull(message = "La promocion es obligatoria")
    private UUID idPromocion;

    @NotNull(message = "El monto aplicado es obligatorio")
    @DecimalMin(value = "0.00", message = "El monto aplicado no puede ser negativo")
    private BigDecimal montoAplicado;
}

