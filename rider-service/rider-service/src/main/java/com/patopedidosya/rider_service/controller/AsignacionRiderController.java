package com.patopedidosya.rider_service.controller;

import com.patopedidosya.rider_service.dto.AsignacionRiderCreateDTO;
import com.patopedidosya.rider_service.dto.AsignacionRiderReadDTO;
import com.patopedidosya.rider_service.dto.AsignacionRiderUpdateDTO;
import com.patopedidosya.rider_service.service.AsignacionRiderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asignaciones-rider")
public class AsignacionRiderController {

    private final AsignacionRiderService asignacionRiderService;

    public AsignacionRiderController(AsignacionRiderService asignacionRiderService) {
        this.asignacionRiderService = asignacionRiderService;
    }

    @GetMapping("/{idAsignacionRider}")
    public ResponseEntity<AsignacionRiderReadDTO> getById(@PathVariable UUID idAsignacionRider) {
        return ResponseEntity.ok(asignacionRiderService.getById(idAsignacionRider));
    }

    @GetMapping
    public ResponseEntity<List<AsignacionRiderReadDTO>> getAll(@RequestParam(required = false) UUID idPedido,
                                                               @RequestParam(required = false) UUID idRider) {
        return ResponseEntity.ok(asignacionRiderService.getAll(idPedido, idRider));
    }

    @PostMapping
    public ResponseEntity<AsignacionRiderReadDTO> create(@Valid @RequestBody AsignacionRiderCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionRiderService.create(dto));
    }

    @PutMapping("/{idAsignacionRider}")
    public ResponseEntity<AsignacionRiderReadDTO> update(@PathVariable UUID idAsignacionRider,
                                                         @Valid @RequestBody AsignacionRiderUpdateDTO dto) {
        return ResponseEntity.ok(asignacionRiderService.update(idAsignacionRider, dto));
    }

    @DeleteMapping("/{idAsignacionRider}")
    public ResponseEntity<Void> delete(@PathVariable UUID idAsignacionRider) {
        asignacionRiderService.delete(idAsignacionRider);
        return ResponseEntity.noContent().build();
    }
}
