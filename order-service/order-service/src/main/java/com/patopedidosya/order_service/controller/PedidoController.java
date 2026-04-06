package com.patopedidosya.order_service.controller;

import com.patopedidosya.order_service.dto.HistorialEstadoPedidoReadDTO;
import com.patopedidosya.order_service.dto.PedidoCambioEstadoDTO;
import com.patopedidosya.order_service.dto.PedidoCreateDTO;
import com.patopedidosya.order_service.dto.PedidoReadDTO;
import com.patopedidosya.order_service.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoReadDTO> getById(@PathVariable UUID idPedido) {
        return ResponseEntity.ok(pedidoService.getById(idPedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoReadDTO>> getAll(@RequestParam(required = false) UUID idUsuario,
                                                      @RequestParam(required = false) UUID idLocal) {
        if (idUsuario != null) {
            return ResponseEntity.ok(pedidoService.getByUsuario(idUsuario));
        }
        if (idLocal != null) {
            return ResponseEntity.ok(pedidoService.getByLocal(idLocal));
        }
        return ResponseEntity.ok(pedidoService.getAll());
    }

    @PostMapping
    public ResponseEntity<PedidoReadDTO> create(@Valid @RequestBody PedidoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.create(dto));
    }

    @PatchMapping("/{idPedido}/estado")
    public ResponseEntity<PedidoReadDTO> cambiarEstado(@PathVariable UUID idPedido,
                                                       @Valid @RequestBody PedidoCambioEstadoDTO dto) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(idPedido, dto));
    }

    @GetMapping("/{idPedido}/historial")
    public ResponseEntity<List<HistorialEstadoPedidoReadDTO>> getHistorial(@PathVariable UUID idPedido) {
        return ResponseEntity.ok(pedidoService.getHistorial(idPedido));
    }
}
