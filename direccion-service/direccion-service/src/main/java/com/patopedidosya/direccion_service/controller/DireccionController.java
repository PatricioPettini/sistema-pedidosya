package com.patopedidosya.direccion_service.controller;

import com.patopedidosya.direccion_service.dto.DireccionCreateDTO;
import com.patopedidosya.direccion_service.dto.DireccionReadDTO;
import com.patopedidosya.direccion_service.dto.DireccionUpdateDTO;
import com.patopedidosya.direccion_service.service.IDireccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    private final IDireccionService direccionService;

    public DireccionController(IDireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @GetMapping("/{idDireccion}")
    public ResponseEntity<DireccionReadDTO> getDireccion(@PathVariable UUID idDireccion){
        return ResponseEntity.ok(direccionService.getDireccionDto(idDireccion));
    }

    @PutMapping("/{idDireccion}")
    public ResponseEntity<DireccionReadDTO> updateDireccion(@PathVariable UUID idDireccion,@Valid @RequestBody DireccionUpdateDTO dto){
        return ResponseEntity.ok(direccionService.updateDireccion(idDireccion, dto));
    }

    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<String> deleteDireccion(@PathVariable UUID idDireccion) {
        direccionService.deleteDireccion(idDireccion);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<DireccionReadDTO> createDireccion(@Valid @RequestBody DireccionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(direccionService.createDireccion(dto));
    }

}
