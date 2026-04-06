package com.patopedidosya.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDireccionCreateDTO {
    @NotNull(message = "El usuario es obligatorio")
    private UUID idUsuario;

    @NotNull(message = "La direccion es obligatoria")
    private UUID idDireccion;

    @NotBlank(message = "El alias es obligatorio")
    @Size(max = 100, message = "El alias no puede superar los 100 caracteres")
    private String alias;

    @NotNull(message = "El indicador de principal es obligatorio")
    private Boolean esPrincipal;
}
