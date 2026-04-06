package com.patopedidosya.rider_service.controller;

import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderCreateDTO;
import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderReadDTO;
import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderUpdateDTO;
import com.patopedidosya.rider_service.service.EstadoAsignacionRiderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estados-asignacion-rider")
public class EstadoAsignacionRiderController {

    private final EstadoAsignacionRiderService estadoAsignacionRiderService;

    public EstadoAsignacionRiderController(EstadoAsignacionRiderService estadoAsignacionRiderService) {
        this.estadoAsignacionRiderService = estadoAsignacionRiderService;
    }

    @GetMapping("/{idEstadoAsignacionRider}")
    public ResponseEntity<EstadoAsignacionRiderReadDTO> getById(@PathVariable UUID idEstadoAsignacionRider) {
        return ResponseEntity.ok(estadoAsignacionRiderService.getById(idEstadoAsignacionRider));
    }

    @GetMapping
    public ResponseEntity<List<EstadoAsignacionRiderReadDTO>> getAll() {
        return ResponseEntity.ok(estadoAsignacionRiderService.getAll());
    }

    @PostMapping
    public ResponseEntity<EstadoAsignacionRiderReadDTO> create(@Valid @RequestBody EstadoAsignacionRiderCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoAsignacionRiderService.create(dto));
    }

    @PutMapping("/{idEstadoAsignacionRider}")
    public ResponseEntity<EstadoAsignacionRiderReadDTO> update(@PathVariable UUID idEstadoAsignacionRider,
                                                               @Valid @RequestBody EstadoAsignacionRiderUpdateDTO dto) {
        return ResponseEntity.ok(estadoAsignacionRiderService.update(idEstadoAsignacionRider, dto));
    }

    @DeleteMapping("/{idEstadoAsignacionRider}")
    public ResponseEntity<Void> delete(@PathVariable UUID idEstadoAsignacionRider) {
        estadoAsignacionRiderService.delete(idEstadoAsignacionRider);
        return ResponseEntity.noContent().build();
    }
}

