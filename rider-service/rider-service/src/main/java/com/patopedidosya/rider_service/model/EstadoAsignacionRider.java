package com.patopedidosya.rider_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoAsignacionRider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idEstadoAsignacionRider;

    private String nombre;
    private String descripcion;
    private Integer orden;
}

