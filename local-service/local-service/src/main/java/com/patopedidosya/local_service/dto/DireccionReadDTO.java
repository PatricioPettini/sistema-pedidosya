package com.patopedidosya.local_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionReadDTO {
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
