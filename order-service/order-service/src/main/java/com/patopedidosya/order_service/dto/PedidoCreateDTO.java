package com.patopedidosya.order_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {
    @NotNull(message = "El usuario es obligatorio")
    private UUID idUsuario;

    @NotNull(message = "El local es obligatorio")
    private UUID idLocal;

    @NotBlank(message = "La direccion de entrega es obligatoria")
    @Size(max = 255, message = "La direccion de entrega no puede superar los 255 caracteres")
    private String direccionEntrega;

    @NotBlank(message = "La ciudad de entrega es obligatoria")
    @Size(max = 100, message = "La ciudad de entrega no puede superar los 100 caracteres")
    private String ciudadEntrega;

    @Size(max = 255, message = "La referencia de entrega no puede superar los 255 caracteres")
    private String referenciaEntrega;

    @NotNull(message = "El costo de envio es obligatorio")
    @DecimalMin(value = "0.00", message = "El costo de envio no puede ser negativo")
    private BigDecimal costoEnvio;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor a 0")
    private BigDecimal subtotal;

    @NotNull(message = "El descuento es obligatorio")
    @DecimalMin(value = "0.00", message = "El descuento no puede ser negativo")
    private BigDecimal descuento;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;

    @NotNull(message = "El estado inicial es obligatorio")
    private UUID idEstadoPedido;

    private LocalDateTime fechaEntrega;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    private List<PedidoDetalleCreateDTO> detalles;

    private List<PedidoPromocionCreateDTO> promociones;
}

