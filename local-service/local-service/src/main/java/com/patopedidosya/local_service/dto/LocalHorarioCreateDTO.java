package com.patopedidosya.local_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalHorarioCreateDTO {
    @NotNull(message = "El local es obligatorio")
    private UUID idLocal;

    @NotNull(message = "El dia de la semana es obligatorio")
    private DayOfWeek diaSemana;

    @NotNull(message = "La hora de apertura es obligatoria")
    private LocalTime horaApertura;

    @NotNull(message = "La hora de cierre es obligatoria")
    private LocalTime horaCierre;

    @NotNull(message = "El estado abierto es obligatorio")
    private Boolean abierto;
}

