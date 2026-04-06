package com.patopedidosya.user_service.controller;

import com.patopedidosya.user_service.dto.UsuarioReadDTO;
import com.patopedidosya.user_service.dto.UsuarioUpdateDTO;
import com.patopedidosya.user_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioReadDTO> getById(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.getById(idUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioReadDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioReadDTO> update(@PathVariable UUID idUsuario,
                                                 @Valid @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.update(idUsuario, dto));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@PathVariable UUID idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
