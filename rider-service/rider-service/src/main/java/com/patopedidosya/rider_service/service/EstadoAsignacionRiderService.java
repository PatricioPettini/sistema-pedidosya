package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderCreateDTO;
import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderReadDTO;
import com.patopedidosya.rider_service.dto.EstadoAsignacionRiderUpdateDTO;
import com.patopedidosya.rider_service.model.EstadoAsignacionRider;
import com.patopedidosya.rider_service.repository.IEstadoAsignacionRiderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EstadoAsignacionRiderService {

    private final IEstadoAsignacionRiderRepository estadoAsignacionRiderRepository;

    public EstadoAsignacionRiderService(IEstadoAsignacionRiderRepository estadoAsignacionRiderRepository) {
        this.estadoAsignacionRiderRepository = estadoAsignacionRiderRepository;
    }

    public EstadoAsignacionRiderReadDTO getById(UUID idEstadoAsignacionRider) {
        return toReadDto(getEntity(idEstadoAsignacionRider));
    }

    public List<EstadoAsignacionRiderReadDTO> getAll() {
        List<EstadoAsignacionRiderReadDTO> response = new ArrayList<>();
        for (EstadoAsignacionRider estado : estadoAsignacionRiderRepository.findAll()) {
            response.add(toReadDto(estado));
        }
        return response;
    }

    public EstadoAsignacionRiderReadDTO create(EstadoAsignacionRiderCreateDTO dto) {
        EstadoAsignacionRider estado = new EstadoAsignacionRider();
        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());
        estado.setOrden(dto.getOrden());
        return toReadDto(estadoAsignacionRiderRepository.save(estado));
    }

    public EstadoAsignacionRiderReadDTO update(UUID idEstadoAsignacionRider, EstadoAsignacionRiderUpdateDTO dto) {
        EstadoAsignacionRider estado = getEntity(idEstadoAsignacionRider);
        if (dto.getNombre() != null) estado.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) estado.setDescripcion(dto.getDescripcion());
        if (dto.getOrden() != null) estado.setOrden(dto.getOrden());
        return toReadDto(estadoAsignacionRiderRepository.save(estado));
    }

    public void delete(UUID idEstadoAsignacionRider) {
        estadoAsignacionRiderRepository.delete(getEntity(idEstadoAsignacionRider));
    }

    public EstadoAsignacionRider getEntity(UUID idEstadoAsignacionRider) {
        return estadoAsignacionRiderRepository.findById(idEstadoAsignacionRider)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estado de asignacion rider no encontrado con id: " + idEstadoAsignacionRider
                ));
    }

    public EstadoAsignacionRiderReadDTO toReadDto(EstadoAsignacionRider estado) {
        EstadoAsignacionRiderReadDTO dto = new EstadoAsignacionRiderReadDTO();
        dto.setIdEstadoAsignacionRider(estado.getIdEstadoAsignacionRider());
        dto.setNombre(estado.getNombre());
        dto.setDescripcion(estado.getDescripcion());
        dto.setOrden(estado.getOrden());
        return dto;
    }
}

