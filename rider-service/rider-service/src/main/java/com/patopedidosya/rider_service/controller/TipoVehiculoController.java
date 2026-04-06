package com.patopedidosya.rider_service.controller;

import com.patopedidosya.rider_service.dto.TipoVehiculoCreateDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoReadDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoUpdateDTO;
import com.patopedidosya.rider_service.service.ITipoVehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tipos-vehiculo")
public class TipoVehiculoController {

    private final ITipoVehiculoService tipoVehiculoService;

    public TipoVehiculoController(ITipoVehiculoService tipoVehiculoService) {
        this.tipoVehiculoService = tipoVehiculoService;
    }

    @GetMapping("/{idTipoVehiculo}")
    public ResponseEntity<TipoVehiculoReadDTO> getTipoVehiculo(@PathVariable UUID idTipoVehiculo) {
        return ResponseEntity.ok(tipoVehiculoService.getTipoVehiculoDto(idTipoVehiculo));
    }

    @GetMapping
    public ResponseEntity<List<TipoVehiculoReadDTO>> getAllTiposVehiculo() {
        return ResponseEntity.ok(tipoVehiculoService.getAll());
    }

    @PostMapping
    public ResponseEntity<TipoVehiculoReadDTO> createTipoVehiculo(@Valid @RequestBody TipoVehiculoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoVehiculoService.createTipoVehiculo(dto));
    }

    @PutMapping("/{idTipoVehiculo}")
    public ResponseEntity<TipoVehiculoReadDTO> updateTipoVehiculo(
            @PathVariable UUID idTipoVehiculo,
            @Valid @RequestBody TipoVehiculoUpdateDTO dto) {
        return ResponseEntity.ok(tipoVehiculoService.updateTipoVehiculo(idTipoVehiculo, dto));
    }

    @DeleteMapping("/{idTipoVehiculo}")
    public ResponseEntity<Void> deleteTipoVehiculo(@PathVariable UUID idTipoVehiculo) {
        tipoVehiculoService.deleteTipoVehiculo(idTipoVehiculo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idTipoVehiculo}")
    public ResponseEntity<TipoVehiculoReadDTO> patchTipoVehiculo(
            @PathVariable UUID idTipoVehiculo,
            @RequestBody TipoVehiculoUpdateDTO dto) {
        return ResponseEntity.ok(tipoVehiculoService.patchTipoVehiculo(idTipoVehiculo, dto));
    }
}