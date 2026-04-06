package com.patopedidosya.cart_service.controller;

import com.patopedidosya.cart_service.dto.*;
import com.patopedidosya.cart_service.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoReadDTO> getById(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.getById(idCarrito));
    }

    @GetMapping
    public ResponseEntity<List<CarritoReadDTO>> getByUsuario(@RequestParam UUID idUsuario) {
        return ResponseEntity.ok(carritoService.getByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<CarritoReadDTO> create(@Valid @RequestBody CarritoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.create(dto));
    }

    @PatchMapping("/{idCarrito}/cerrar")
    public ResponseEntity<Void> cerrar(@PathVariable UUID idCarrito) {
        carritoService.cerrar(idCarrito);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCarrito}/detalles")
    public ResponseEntity<CarritoDetalleReadDTO> agregarDetalle(@PathVariable UUID idCarrito,
                                                                @Valid @RequestBody CarritoDetalleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.agregarDetalle(idCarrito, dto));
    }

    @PutMapping("/detalles/{idCarritoDetalle}")
    public ResponseEntity<CarritoDetalleReadDTO> actualizarDetalle(@PathVariable UUID idCarritoDetalle,
                                                                   @Valid @RequestBody CarritoDetalleUpdateDTO dto) {
        return ResponseEntity.ok(carritoService.actualizarDetalle(idCarritoDetalle, dto));
    }

    @DeleteMapping("/detalles/{idCarritoDetalle}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable UUID idCarritoDetalle) {
        carritoService.eliminarDetalle(idCarritoDetalle);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCarrito}/promociones")
    public ResponseEntity<CarritoPromocionReadDTO> agregarPromocion(@PathVariable UUID idCarrito,
                                                                    @Valid @RequestBody CarritoPromocionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.agregarPromocion(idCarrito, dto));
    }

    @DeleteMapping("/promociones/{idCarritoPromocion}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable UUID idCarritoPromocion) {
        carritoService.eliminarPromocion(idCarritoPromocion);
        return ResponseEntity.noContent().build();
    }
}

