package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.RubroLocalCreateDTO;
import com.patopedidosya.local_service.dto.RubroLocalReadDTO;
import com.patopedidosya.local_service.dto.RubroLocalUpdateDTO;
import com.patopedidosya.local_service.service.RubroLocalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rubros-local")
public class RubroLocalController {

    private final RubroLocalService rubroLocalService;

    public RubroLocalController(RubroLocalService rubroLocalService) {
        this.rubroLocalService = rubroLocalService;
    }

    @GetMapping("/{idRubroLocal}")
    public ResponseEntity<RubroLocalReadDTO> getById(@PathVariable UUID idRubroLocal) {
        return ResponseEntity.ok(rubroLocalService.getById(idRubroLocal));
    }

    @GetMapping
    public ResponseEntity<List<RubroLocalReadDTO>> getAll() {
        return ResponseEntity.ok(rubroLocalService.getAll());
    }

    @PostMapping
    public ResponseEntity<RubroLocalReadDTO> create(@Valid @RequestBody RubroLocalCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rubroLocalService.create(dto));
    }

    @PutMapping("/{idRubroLocal}")
    public ResponseEntity<RubroLocalReadDTO> update(@PathVariable UUID idRubroLocal,
                                                    @Valid @RequestBody RubroLocalUpdateDTO dto) {
        return ResponseEntity.ok(rubroLocalService.update(idRubroLocal, dto));
    }

    @DeleteMapping("/{idRubroLocal}")
    public ResponseEntity<Void> delete(@PathVariable UUID idRubroLocal) {
        rubroLocalService.delete(idRubroLocal);
        return ResponseEntity.noContent().build();
    }
}

