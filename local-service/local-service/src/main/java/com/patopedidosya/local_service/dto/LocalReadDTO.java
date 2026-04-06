package com.patopedidosya.local_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalReadDTO {
    private UUID idLocal;
    private String nombre;
    private String telefono;
    private String email;
    private Boolean activo;
    private RubroLocalReadDTO rubroLocal;
    private DireccionReadDTO direccion;
}
