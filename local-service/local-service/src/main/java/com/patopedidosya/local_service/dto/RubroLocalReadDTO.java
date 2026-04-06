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
public class RubroLocalReadDTO {
    private UUID idRubroLocal;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

