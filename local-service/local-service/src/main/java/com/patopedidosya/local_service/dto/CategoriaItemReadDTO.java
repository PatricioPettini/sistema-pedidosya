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
public class CategoriaItemReadDTO {
    private UUID idCategoriaItem;
    private UUID idLocal;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Integer orden;
}

