package com.patopedidosya.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDireccionReadDTO {
    private UUID idUserDireccion;
    private UUID idUsuario;
    private DireccionReadDTO direccion;
    private String alias;
    private Boolean esPrincipal;
}
