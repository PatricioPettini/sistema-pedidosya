package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.LocalHorarioCreateDTO;
import com.patopedidosya.local_service.dto.LocalHorarioReadDTO;
import com.patopedidosya.local_service.dto.LocalHorarioUpdateDTO;
import com.patopedidosya.local_service.service.LocalHorarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locales-horario")
public class LocalHorarioController {

    private final LocalHorarioService localHorarioService;

    public LocalHorarioController(LocalHorarioService localHorarioService) {
        this.localHorarioService = localHorarioService;
    }

    @GetMapping("/{idLocalHorario}")
    public ResponseEntity<LocalHorarioReadDTO> getById(@PathVariable UUID idLocalHorario) {
        return ResponseEntity.ok(localHorarioService.getById(idLocalHorario));
    }

    @GetMapping
    public ResponseEntity<List<LocalHorarioReadDTO>> getAll(@RequestParam(required = false) UUID idLocal) {
        if (idLocal != null) {
            return ResponseEntity.ok(localHorarioService.getByLocal(idLocal));
        }
        return ResponseEntity.ok(localHorarioService.getAll());
    }

    @PostMapping
    public ResponseEntity<LocalHorarioReadDTO> create(@Valid @RequestBody LocalHorarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localHorarioService.create(dto));
    }

    @PutMapping("/{idLocalHorario}")
    public ResponseEntity<LocalHorarioReadDTO> update(@PathVariable UUID idLocalHorario,
                                                      @Valid @RequestBody LocalHorarioUpdateDTO dto) {
        return ResponseEntity.ok(localHorarioService.update(idLocalHorario, dto));
    }

    @DeleteMapping("/{idLocalHorario}")
    public ResponseEntity<Void> delete(@PathVariable UUID idLocalHorario) {
        localHorarioService.delete(idLocalHorario);
        return ResponseEntity.noContent().build();
    }
}

