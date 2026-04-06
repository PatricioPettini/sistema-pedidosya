package com.patopedidosya.notification_service.controller;

import com.patopedidosya.notification_service.dto.NotificacionCreateDTO;
import com.patopedidosya.notification_service.dto.NotificacionReadDTO;
import com.patopedidosya.notification_service.dto.NotificacionUpdateDTO;
import com.patopedidosya.notification_service.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/{idNotificacion}")
    public ResponseEntity<NotificacionReadDTO> getById(@PathVariable UUID idNotificacion) {
        return ResponseEntity.ok(notificacionService.getById(idNotificacion));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionReadDTO>> getAll(@RequestParam(required = false) UUID idUsuario) {
        return ResponseEntity.ok(notificacionService.getAll(idUsuario));
    }

    @PostMapping
    public ResponseEntity<NotificacionReadDTO> create(@Valid @RequestBody NotificacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.create(dto));
    }

    @PutMapping("/{idNotificacion}")
    public ResponseEntity<NotificacionReadDTO> update(@PathVariable UUID idNotificacion,
                                                      @Valid @RequestBody NotificacionUpdateDTO dto) {
        return ResponseEntity.ok(notificacionService.update(idNotificacion, dto));
    }

    @DeleteMapping("/{idNotificacion}")
    public ResponseEntity<Void> delete(@PathVariable UUID idNotificacion) {
        notificacionService.delete(idNotificacion);
        return ResponseEntity.noContent().build();
    }
}
