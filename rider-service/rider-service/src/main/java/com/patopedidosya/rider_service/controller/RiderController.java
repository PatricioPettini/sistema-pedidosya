package com.patopedidosya.rider_service.controller;

import com.patopedidosya.rider_service.dto.RiderCreateDTO;
import com.patopedidosya.rider_service.dto.RiderReadDTO;
import com.patopedidosya.rider_service.dto.RiderUpdateDTO;
import com.patopedidosya.rider_service.service.IRiderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/riders")
public class RiderController {

    private final IRiderService riderService;

    public RiderController(IRiderService riderService) {
        this.riderService = riderService;
    }

    @GetMapping("/{idRider}")
    public ResponseEntity<RiderReadDTO> getRider(@PathVariable UUID idRider) {
        return ResponseEntity.ok(riderService.getRiderDto(idRider));
    }

    @GetMapping
    public ResponseEntity<List<RiderReadDTO>> getAllRiders() {
        return ResponseEntity.ok(riderService.getAll());
    }

    @PostMapping
    public ResponseEntity<RiderReadDTO> createRider(@Valid @RequestBody RiderCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(riderService.createRider(dto));
    }

    @PutMapping("/{idRider}")
    public ResponseEntity<RiderReadDTO> updateRider(
            @PathVariable UUID idRider,
            @Valid @RequestBody RiderUpdateDTO dto) {
        return ResponseEntity.ok(riderService.updateRider(idRider, dto));
    }

    @DeleteMapping("/{idRider}")
    public ResponseEntity<Void> deleteRider(@PathVariable UUID idRider) {
        riderService.deleteRider(idRider);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idRider}")
    public ResponseEntity<RiderReadDTO> patchRider(
            @PathVariable UUID idRider,
            @RequestBody RiderUpdateDTO dto) {
        return ResponseEntity.ok(riderService.patchRider(idRider, dto));
    }
}