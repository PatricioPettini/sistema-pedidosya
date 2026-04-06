package com.patopedidosya.order_service.service;

import com.patopedidosya.order_service.dto.EstadoPedidoCreateDTO;
import com.patopedidosya.order_service.dto.EstadoPedidoReadDTO;
import com.patopedidosya.order_service.dto.EstadoPedidoUpdateDTO;
import com.patopedidosya.order_service.model.EstadoPedido;
import com.patopedidosya.order_service.repository.IEstadoPedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EstadoPedidoService {

    private final IEstadoPedidoRepository estadoPedidoRepository;

    public EstadoPedidoService(IEstadoPedidoRepository estadoPedidoRepository) {
        this.estadoPedidoRepository = estadoPedidoRepository;
    }

    public EstadoPedidoReadDTO getById(UUID idEstadoPedido) {
        return toReadDto(getEntity(idEstadoPedido));
    }

    public List<EstadoPedidoReadDTO> getAll() {
        List<EstadoPedidoReadDTO> response = new ArrayList<>();
        for (EstadoPedido estadoPedido : estadoPedidoRepository.findAll()) {
            response.add(toReadDto(estadoPedido));
        }
        return response;
    }

    public EstadoPedidoReadDTO create(EstadoPedidoCreateDTO dto) {
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setNombre(dto.getNombre());
        estadoPedido.setDescripcion(dto.getDescripcion());
        estadoPedido.setOrden(dto.getOrden());
        estadoPedido.setEsFinal(dto.getEsFinal());
        return toReadDto(estadoPedidoRepository.save(estadoPedido));
    }

    public EstadoPedidoReadDTO update(UUID idEstadoPedido, EstadoPedidoUpdateDTO dto) {
        EstadoPedido estadoPedido = getEntity(idEstadoPedido);
        if (dto.getNombre() != null) estadoPedido.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) estadoPedido.setDescripcion(dto.getDescripcion());
        if (dto.getOrden() != null) estadoPedido.setOrden(dto.getOrden());
        if (dto.getEsFinal() != null) estadoPedido.setEsFinal(dto.getEsFinal());
        return toReadDto(estadoPedidoRepository.save(estadoPedido));
    }

    public void delete(UUID idEstadoPedido) {
        estadoPedidoRepository.delete(getEntity(idEstadoPedido));
    }

    public EstadoPedido getEntity(UUID idEstadoPedido) {
        return estadoPedidoRepository.findById(idEstadoPedido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estado de pedido no encontrado con id: " + idEstadoPedido
                ));
    }

    public EstadoPedidoReadDTO toReadDto(EstadoPedido estadoPedido) {
        EstadoPedidoReadDTO dto = new EstadoPedidoReadDTO();
        dto.setIdEstadoPedido(estadoPedido.getIdEstadoPedido());
        dto.setNombre(estadoPedido.getNombre());
        dto.setDescripcion(estadoPedido.getDescripcion());
        dto.setOrden(estadoPedido.getOrden());
        dto.setEsFinal(estadoPedido.getEsFinal());
        return dto;
    }
}

