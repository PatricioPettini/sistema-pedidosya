package com.patopedidosya.payment_service.controller;

import com.patopedidosya.payment_service.dto.PagoCreateDTO;
import com.patopedidosya.payment_service.dto.PagoReadDTO;
import com.patopedidosya.payment_service.dto.PagoUpdateDTO;
import com.patopedidosya.payment_service.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<PagoReadDTO> getById(@PathVariable UUID idPago) {
        return ResponseEntity.ok(pagoService.getById(idPago));
    }

    @GetMapping
    public ResponseEntity<List<PagoReadDTO>> getAll(@RequestParam(required = false) UUID idPedido) {
        if (idPedido != null) {
            return ResponseEntity.ok(List.of(pagoService.getByPedido(idPedido)));
        }
        return ResponseEntity.ok(pagoService.getAll());
    }

    @PostMapping
    public ResponseEntity<PagoReadDTO> create(@Valid @RequestBody PagoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.create(dto));
    }

    @PutMapping("/{idPago}")
    public ResponseEntity<PagoReadDTO> update(@PathVariable UUID idPago,
                                              @Valid @RequestBody PagoUpdateDTO dto) {
        return ResponseEntity.ok(pagoService.update(idPago, dto));
    }

    @DeleteMapping("/{idPago}")
    public ResponseEntity<Void> delete(@PathVariable UUID idPago) {
        pagoService.delete(idPago);
        return ResponseEntity.noContent().build();
    }
}
