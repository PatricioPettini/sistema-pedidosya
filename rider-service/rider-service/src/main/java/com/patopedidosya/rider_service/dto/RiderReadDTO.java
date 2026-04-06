package com.patopedidosya.rider_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiderReadDTO {

    private UUID idRider;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private Boolean activo;
    private TipoVehiculoReadDTO tipoVehiculo;
}
