package com.patopedidosya.cart_service.dto;

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
public class ItemReadDTO {
    private UUID idItem;
    private UUID idLocal;
    private CategoriaItemReadDTO categoriaItem;
    private String nombre;
    private BigDecimal precio;
    private Boolean disponible;
}

