package com.patopedidosya.direccion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionCreateDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 100, message = "La calle no puede superar los 100 caracteres")
    private String calle;

    @NotNull(message = "El numero es obligatorio")
    @Positive(message = "El numero debe ser mayor a 0")
    private Integer numero;

    @Size(max = 20, message = "El piso no puede superar los 20 caracteres")
    private String piso;

    @Size(max = 20, message = "El depto no puede superar los 20 caracteres")
    private String depto;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres")
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 100, message = "La provincia no puede superar los 100 caracteres")
    private String provincia;

    @NotBlank(message = "El codigo postal es obligatorio")
    @Size(max = 20, message = "El codigo postal no puede superar los 20 caracteres")
    private String codigoPostal;

    @Size(max = 255, message = "La referencia no puede superar los 255 caracteres")
    private String referencia;
}
