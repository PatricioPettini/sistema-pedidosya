package com.patopedidosya.rider_service.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoVehiculoUpdateDTO {

    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    private Boolean activo;
}