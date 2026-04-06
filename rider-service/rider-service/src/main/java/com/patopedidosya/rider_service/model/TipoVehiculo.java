package com.patopedidosya.rider_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTipoVehiculo;

    private String nombre;
    private Boolean activo;

    @OneToMany(mappedBy = "tipoVehiculo")
    private List<Rider> riders;
}
