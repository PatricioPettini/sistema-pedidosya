package com.patopedidosya.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPedido;

    private UUID idUsuario;
    private UUID idLocal;
    private String direccionEntrega;
    private String ciudadEntrega;
    private String referenciaEntrega;
    private BigDecimal costoEnvio;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private UUID idEstadoPedido;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
}

