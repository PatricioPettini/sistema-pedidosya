package com.patopedidosya.local_service.dto;

import com.patopedidosya.local_service.model.TipoDescuento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
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
public class PromocionUpdateDTO {
    private UUID idItem;

    @Size(max = 50, message = "El codigo no puede superar los 50 caracteres")
    private String codigo;

    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    private String descripcion;

    private TipoDescuento tipoDescuento;

    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    private BigDecimal valor;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activo;

    @DecimalMin(value = "0.00", message = "El tope de descuento no puede ser negativo")
    private BigDecimal topeDescuento;
}

