package com.patopedidosya.direccion_service.model;

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
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDireccion;

    private String calle;
    private Integer numero;
    private String piso;
    private String depto;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private String referencia;
}
