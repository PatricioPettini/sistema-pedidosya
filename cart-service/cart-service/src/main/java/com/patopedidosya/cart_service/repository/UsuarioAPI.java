package com.patopedidosya.cart_service.repository;

import com.patopedidosya.cart_service.dto.UsuarioReadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UsuarioAPI {
    @GetMapping("/usuarios/{idUsuario}")
    ResponseEntity<UsuarioReadDTO> getUsuario(@PathVariable UUID idUsuario);
}

