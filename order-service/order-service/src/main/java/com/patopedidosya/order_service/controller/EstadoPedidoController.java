package com.patopedidosya.order_service.controller;

import com.patopedidosya.order_service.dto.EstadoPedidoCreateDTO;
import com.patopedidosya.order_service.dto.EstadoPedidoReadDTO;
import com.patopedidosya.order_service.dto.EstadoPedidoUpdateDTO;
import com.patopedidosya.order_service.service.EstadoPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estados-pedido")
public class EstadoPedidoController {

    private final EstadoPedidoService estadoPedidoService;

    public EstadoPedidoController(EstadoPedidoService estadoPedidoService) {
        this.estadoPedidoService = estadoPedidoService;
    }

    @GetMapping("/{idEstadoPedido}")
    public ResponseEntity<EstadoPedidoReadDTO> getById(@PathVariable UUID idEstadoPedido) {
        return ResponseEntity.ok(estadoPedidoService.getById(idEstadoPedido));
    }

    @GetMapping
    public ResponseEntity<List<EstadoPedidoReadDTO>> getAll() {
        return ResponseEntity.ok(estadoPedidoService.getAll());
    }

    @PostMapping
    public ResponseEntity<EstadoPedidoReadDTO> create(@Valid @RequestBody EstadoPedidoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoPedidoService.create(dto));
    }

    @PutMapping("/{idEstadoPedido}")
    public ResponseEntity<EstadoPedidoReadDTO> update(@PathVariable UUID idEstadoPedido,
                                                      @Valid @RequestBody EstadoPedidoUpdateDTO dto) {
        return ResponseEntity.ok(estadoPedidoService.update(idEstadoPedido, dto));
    }

    @DeleteMapping("/{idEstadoPedido}")
    public ResponseEntity<Void> delete(@PathVariable UUID idEstadoPedido) {
        estadoPedidoService.delete(idEstadoPedido);
        return ResponseEntity.noContent().build();
    }
}

