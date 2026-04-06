package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.PromocionCreateDTO;
import com.patopedidosya.local_service.dto.PromocionReadDTO;
import com.patopedidosya.local_service.dto.PromocionUpdateDTO;
import com.patopedidosya.local_service.service.PromocionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @GetMapping("/{idPromocion}")
    public ResponseEntity<PromocionReadDTO> getById(@PathVariable UUID idPromocion) {
        return ResponseEntity.ok(promocionService.getById(idPromocion));
    }

    @GetMapping
    public ResponseEntity<List<PromocionReadDTO>> getAll(@RequestParam(required = false) UUID idLocal) {
        if (idLocal != null) {
            return ResponseEntity.ok(promocionService.getByLocal(idLocal));
        }
        return ResponseEntity.ok(promocionService.getAll());
    }

    @PostMapping
    public ResponseEntity<PromocionReadDTO> create(@Valid @RequestBody PromocionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.create(dto));
    }

    @PutMapping("/{idPromocion}")
    public ResponseEntity<PromocionReadDTO> update(@PathVariable UUID idPromocion,
                                                   @Valid @RequestBody PromocionUpdateDTO dto) {
        return ResponseEntity.ok(promocionService.update(idPromocion, dto));
    }

    @DeleteMapping("/{idPromocion}")
    public ResponseEntity<Void> delete(@PathVariable UUID idPromocion) {
        promocionService.delete(idPromocion);
        return ResponseEntity.noContent().build();
    }
}
