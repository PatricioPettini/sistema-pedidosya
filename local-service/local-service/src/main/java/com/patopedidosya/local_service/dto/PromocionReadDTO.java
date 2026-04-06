package com.patopedidosya.local_service.dto;

import com.patopedidosya.local_service.model.TipoDescuento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionReadDTO {
    private UUID idPromocion;
    private UUID idLocal;
    private ItemReadDTO item;
    private String codigo;
    private String descripcion;
    private TipoDescuento tipoDescuento;
    private BigDecimal valor;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activo;
    private BigDecimal topeDescuento;
}
