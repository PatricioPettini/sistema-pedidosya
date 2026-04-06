package com.patopedidosya.cart_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoCreateDTO {
    @NotNull(message = "El usuario es obligatorio")
    private UUID idUsuario;

    @NotNull(message = "El local es obligatorio")
    private UUID idLocal;
}

