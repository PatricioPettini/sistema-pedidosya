package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.LocalCreateDTO;
import com.patopedidosya.local_service.dto.LocalReadDTO;
import com.patopedidosya.local_service.dto.LocalUpdateDTO;
import com.patopedidosya.local_service.service.ILocalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locales")
public class LocalController {

    private final ILocalService localService;

    public LocalController(ILocalService localService) {
        this.localService = localService;
    }

    @GetMapping("/{idLocal}")
    public ResponseEntity<LocalReadDTO> getLocal(@PathVariable UUID idLocal) {
        return ResponseEntity.ok(localService.getLocalDto(idLocal));
    }

    @GetMapping
    public ResponseEntity<List<LocalReadDTO>> getAllLocal() {
        return ResponseEntity.ok(localService.getAll());
    }

    @PostMapping
    public ResponseEntity<LocalReadDTO> createLocal(@Valid @RequestBody LocalCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(localService.createLocal(dto));
    }

    @PutMapping("/{idLocal}")
    public ResponseEntity<LocalReadDTO> updateLocal(
            @PathVariable UUID idLocal,
            @Valid @RequestBody LocalUpdateDTO dto) {
        return ResponseEntity.ok(localService.updateLocal(idLocal, dto));
    }

    @DeleteMapping("/{idLocal}")
    public ResponseEntity<Void> deleteLocal(@PathVariable UUID idLocal) {
        localService.deleteLocal(idLocal);
        return ResponseEntity.noContent().build();
    }
}