package com.patopedidosya.payment_service.service;

import com.patopedidosya.payment_service.dto.PagoCreateDTO;
import com.patopedidosya.payment_service.dto.PagoReadDTO;
import com.patopedidosya.payment_service.dto.PagoUpdateDTO;
import com.patopedidosya.payment_service.dto.PedidoReadDTO;
import com.patopedidosya.payment_service.model.Pago;
import com.patopedidosya.payment_service.repository.IPagoRepository;
import com.patopedidosya.payment_service.repository.PedidoAPI;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final PedidoAPI pedidoAPI;

    public PagoService(IPagoRepository pagoRepository, PedidoAPI pedidoAPI) {
        this.pagoRepository = pagoRepository;
        this.pedidoAPI = pedidoAPI;
    }

    public PagoReadDTO getById(UUID idPago) {
        return toReadDto(getEntity(idPago));
    }

    public PagoReadDTO getByPedido(UUID idPedido) {
        validarPedido(idPedido);
        Pago pago = pagoRepository.findByIdPedido(idPedido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago no encontrado para el pedido: " + idPedido
                ));
        return toReadDto(pago);
    }

    public List<PagoReadDTO> getAll() {
        List<PagoReadDTO> response = new ArrayList<>();
        for (Pago pago : pagoRepository.findAll()) {
            response.add(toReadDto(pago));
        }
        return response;
    }

    public PagoReadDTO create(PagoCreateDTO dto) {
        validarPedido(dto.getIdPedido());

        if (pagoRepository.findByIdPedido(dto.getIdPedido()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un pago para el pedido: " + dto.getIdPedido()
            );
        }

        Pago pago = new Pago();
        pago.setIdPedido(dto.getIdPedido());
        pago.setMonto(dto.getMonto());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setFormaPago(dto.getFormaPago());
        pago.setFechaPago(LocalDateTime.now());
        pago.setReferenciaTransaccion(dto.getReferenciaTransaccion());
        pago.setProveedorPago(dto.getProveedorPago());
        return toReadDto(pagoRepository.save(pago));
    }

    public PagoReadDTO update(UUID idPago, PagoUpdateDTO dto) {
        Pago pago = getEntity(idPago);

        if (dto.getMonto() != null) pago.setMonto(dto.getMonto());
        if (dto.getEstadoPago() != null) pago.setEstadoPago(dto.getEstadoPago());
        if (dto.getFormaPago() != null) pago.setFormaPago(dto.getFormaPago());
        if (dto.getReferenciaTransaccion() != null) pago.setReferenciaTransaccion(dto.getReferenciaTransaccion());
        if (dto.getProveedorPago() != null) pago.setProveedorPago(dto.getProveedorPago());

        return toReadDto(pagoRepository.save(pago));
    }

    public void delete(UUID idPago) {
        pagoRepository.delete(getEntity(idPago));
    }

    private Pago getEntity(UUID idPago) {
        return pagoRepository.findById(idPago)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago no encontrado con id: " + idPago
                ));
    }

    private void validarPedido(UUID idPedido) {
        try {
            ResponseEntity<PedidoReadDTO> response = pedidoAPI.getPedido(idPedido);
            if (response == null || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido no encontrado con id: " + idPedido);
            }
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido no encontrado con id: " + idPedido);
        }
    }

    private PagoReadDTO toReadDto(Pago pago) {
        PagoReadDTO dto = new PagoReadDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setIdPedido(pago.getIdPedido());
        dto.setMonto(pago.getMonto());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setFormaPago(pago.getFormaPago());
        dto.setFechaPago(pago.getFechaPago());
        dto.setReferenciaTransaccion(pago.getReferenciaTransaccion());
        dto.setProveedorPago(pago.getProveedorPago());
        return dto;
    }
}

