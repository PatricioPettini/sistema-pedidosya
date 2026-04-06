package com.patopedidosya.user_service.controller;

import com.patopedidosya.user_service.dto.UserDireccionCreateDTO;
import com.patopedidosya.user_service.dto.UserDireccionReadDTO;
import com.patopedidosya.user_service.dto.UserDireccionUpdateDTO;
import com.patopedidosya.user_service.service.UserDireccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios-direcciones")
public class UserDireccionController {

    private final UserDireccionService userDireccionService;

    public UserDireccionController(UserDireccionService userDireccionService) {
        this.userDireccionService = userDireccionService;
    }

    @GetMapping("/{idUserDireccion}")
    public ResponseEntity<UserDireccionReadDTO> getById(@PathVariable UUID idUserDireccion) {
        return ResponseEntity.ok(userDireccionService.getById(idUserDireccion));
    }

    @GetMapping
    public ResponseEntity<List<UserDireccionReadDTO>> getByUsuario(@RequestParam UUID idUsuario) {
        return ResponseEntity.ok(userDireccionService.getByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<UserDireccionReadDTO> create(@Valid @RequestBody UserDireccionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userDireccionService.create(dto));
    }

    @PutMapping("/{idUserDireccion}")
    public ResponseEntity<UserDireccionReadDTO> update(@PathVariable UUID idUserDireccion,
                                                       @Valid @RequestBody UserDireccionUpdateDTO dto) {
        return ResponseEntity.ok(userDireccionService.update(idUserDireccion, dto));
    }

    @DeleteMapping("/{idUserDireccion}")
    public ResponseEntity<Void> delete(@PathVariable UUID idUserDireccion) {
        userDireccionService.delete(idUserDireccion);
        return ResponseEntity.noContent().build();
    }
}
