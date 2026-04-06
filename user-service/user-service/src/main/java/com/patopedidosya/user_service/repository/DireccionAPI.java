package com.patopedidosya.user_service.repository;

import com.patopedidosya.user_service.dto.DireccionReadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "direccion-service")
public interface DireccionAPI {

    @GetMapping("/direcciones/{idDireccion}")
    ResponseEntity<DireccionReadDTO> getDireccion(@PathVariable UUID idDireccion);
}
