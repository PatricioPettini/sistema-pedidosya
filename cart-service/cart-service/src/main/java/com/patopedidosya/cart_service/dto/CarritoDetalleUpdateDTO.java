package com.patopedidosya.cart_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDetalleUpdateDTO {
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
}

