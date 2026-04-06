package com.patopedidosya.user_service.dto;

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
public class UserDireccionUpdateDTO {
    private UUID idDireccion;

    @Size(max = 100, message = "El alias no puede superar los 100 caracteres")
    private String alias;

    private Boolean esPrincipal;
}
