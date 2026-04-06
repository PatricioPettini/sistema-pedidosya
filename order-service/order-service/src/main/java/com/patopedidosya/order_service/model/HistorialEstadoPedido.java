package com.patopedidosya.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialEstadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idHistorialEstadoPedido;

    private UUID idPedido;
    private UUID idEstadoAnterior;
    private UUID idEstadoNuevo;
    private LocalDateTime fecha;
    private String detalle;
}

