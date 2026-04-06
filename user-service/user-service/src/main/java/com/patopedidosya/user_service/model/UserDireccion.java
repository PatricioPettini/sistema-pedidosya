package com.patopedidosya.user_service.model;

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
public class UserDireccion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUserDireccion;

    private UUID idUsuario;
    private UUID idDireccion;
    private String alias;
    private Boolean esPrincipal;
}
