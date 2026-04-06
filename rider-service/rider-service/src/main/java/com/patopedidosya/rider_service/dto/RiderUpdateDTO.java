package com.patopedidosya.rider_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class RiderUpdateDTO {

    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @Pattern(regexp = "^[0-9]{7,10}$", message = "El DNI debe contener solo numeros y tener entre 7 y 10 digitos")
    private String dni;

    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres")
    @Pattern(regexp = "^[0-9+()\\-\\s]*$", message = "El telefono contiene caracteres invalidos")
    private String telefono;

    @Email(message = "El email no tiene un formato valido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    private LocalDate fechaNacimiento;

    @PastOrPresent(message = "La fecha de alta no puede ser futura")
    private LocalDate fechaAlta;

    private UUID idTipoVehiculo;
    private Boolean activo;
}
