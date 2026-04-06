package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.*;
import com.patopedidosya.rider_service.model.AsignacionRider;
import com.patopedidosya.rider_service.model.EstadoAsignacionRider;
import com.patopedidosya.rider_service.model.Rider;
import com.patopedidosya.rider_service.repository.IAsignacionRiderRepository;
import com.patopedidosya.rider_service.repository.PedidoAPI;
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
public class AsignacionRiderService {

    private final IAsignacionRiderRepository asignacionRiderRepository;
    private final IRiderService riderService;
    private final EstadoAsignacionRiderService estadoAsignacionRiderService;
    private final PedidoAPI pedidoAPI;

    public AsignacionRiderService(IAsignacionRiderRepository asignacionRiderRepository,
                                  IRiderService riderService,
                                  EstadoAsignacionRiderService estadoAsignacionRiderService,
                                  PedidoAPI pedidoAPI) {
        this.asignacionRiderRepository = asignacionRiderRepository;
        this.riderService = riderService;
        this.estadoAsignacionRiderService = estadoAsignacionRiderService;
        this.pedidoAPI = pedidoAPI;
    }

    public AsignacionRiderReadDTO getById(UUID idAsignacionRider) {
        return toReadDto(getEntity(idAsignacionRider));
    }

    public List<AsignacionRiderReadDTO> getAll(UUID idPedido, UUID idRider) {
        List<AsignacionRider> asignaciones;

        if (idPedido != null) {
            validarPedido(idPedido);
            asignaciones = asignacionRiderRepository.findByIdPedido(idPedido);
        } else if (idRider != null) {
            riderService.getRiderDto(idRider);
            asignaciones = asignacionRiderRepository.findByIdRider(idRider);
        } else {
            asignaciones = asignacionRiderRepository.findAll();
        }

        List<AsignacionRiderReadDTO> response = new ArrayList<>();
        for (AsignacionRider asignacion : asignaciones) {
            response.add(toReadDto(asignacion));
        }
        return response;
    }

    public AsignacionRiderReadDTO create(AsignacionRiderCreateDTO dto) {
        validarPedido(dto.getIdPedido());
        RiderReadDTO rider = riderService.getRiderDto(dto.getIdRider());
        EstadoAsignacionRider estado = estadoAsignacionRiderService.getEntity(dto.getIdEstadoAsignacionRider());

        AsignacionRider asignacion = new AsignacionRider();
        asignacion.setIdPedido(dto.getIdPedido());
        asignacion.setIdRider(rider.getIdRider());
        asignacion.setIdEstadoAsignacionRider(estado.getIdEstadoAsignacionRider());
        asignacion.setFechaAsignacion(LocalDateTime.now());
        asignacion.setObservaciones(dto.getObservaciones());
        return toReadDto(asignacionRiderRepository.save(asignacion));
    }

    public AsignacionRiderReadDTO update(UUID idAsignacionRider, AsignacionRiderUpdateDTO dto) {
        AsignacionRider asignacion = getEntity(idAsignacionRider);

        if (dto.getIdRider() != null) {
            riderService.getRiderDto(dto.getIdRider());
            asignacion.setIdRider(dto.getIdRider());
        }

        if (dto.getIdEstadoAsignacionRider() != null) {
            estadoAsignacionRiderService.getEntity(dto.getIdEstadoAsignacionRider());
            asignacion.setIdEstadoAsignacionRider(dto.getIdEstadoAsignacionRider());
        }

        if (dto.getObservaciones() != null) {
            asignacion.setObservaciones(dto.getObservaciones());
        }

        return toReadDto(asignacionRiderRepository.save(asignacion));
    }

    public void delete(UUID idAsignacionRider) {
        asignacionRiderRepository.delete(getEntity(idAsignacionRider));
    }

    private AsignacionRider getEntity(UUID idAsignacionRider) {
        return asignacionRiderRepository.findById(idAsignacionRider)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Asignacion rider no encontrada con id: " + idAsignacionRider
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

    private AsignacionRiderReadDTO toReadDto(AsignacionRider asignacion) {
        AsignacionRiderReadDTO dto = new AsignacionRiderReadDTO();
        dto.setIdAsignacionRider(asignacion.getIdAsignacionRider());
        dto.setIdPedido(asignacion.getIdPedido());
        dto.setRider(riderService.getRiderDto(asignacion.getIdRider()));
        dto.setEstadoAsignacionRider(
                estadoAsignacionRiderService.toReadDto(
                        estadoAsignacionRiderService.getEntity(asignacion.getIdEstadoAsignacionRider())
                )
        );
        dto.setFechaAsignacion(asignacion.getFechaAsignacion());
        dto.setObservaciones(asignacion.getObservaciones());
        return dto;
    }
}

