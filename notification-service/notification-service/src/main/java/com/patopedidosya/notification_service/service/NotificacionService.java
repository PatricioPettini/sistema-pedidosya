package com.patopedidosya.notification_service.service;

import com.patopedidosya.notification_service.dto.NotificacionCreateDTO;
import com.patopedidosya.notification_service.dto.NotificacionReadDTO;
import com.patopedidosya.notification_service.dto.NotificacionUpdateDTO;
import com.patopedidosya.notification_service.model.EstadoNotificacion;
import com.patopedidosya.notification_service.model.Notificacion;
import com.patopedidosya.notification_service.repository.INotificacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotificacionService {

    private final INotificacionRepository notificacionRepository;

    public NotificacionService(INotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public NotificacionReadDTO getById(UUID idNotificacion) {
        return toReadDto(getEntity(idNotificacion));
    }

    public List<NotificacionReadDTO> getAll(UUID idUsuario) {
        List<Notificacion> notificaciones = idUsuario != null
                ? notificacionRepository.findByIdUsuario(idUsuario)
                : notificacionRepository.findAll();

        List<NotificacionReadDTO> response = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            response.add(toReadDto(notificacion));
        }
        return response;
    }

    public NotificacionReadDTO create(NotificacionCreateDTO dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuario(dto.getIdUsuario());
        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setCanal(dto.getCanal());
        notificacion.setEstado(dto.getEstado());
        notificacion.setFechaCreacion(LocalDateTime.now());
        if (dto.getEstado() == EstadoNotificacion.ENVIADA) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
        return toReadDto(notificacionRepository.save(notificacion));
    }

    public NotificacionReadDTO update(UUID idNotificacion, NotificacionUpdateDTO dto) {
        Notificacion notificacion = getEntity(idNotificacion);
        if (dto.getTitulo() != null) notificacion.setTitulo(dto.getTitulo());
        if (dto.getMensaje() != null) notificacion.setMensaje(dto.getMensaje());
        if (dto.getCanal() != null) notificacion.setCanal(dto.getCanal());
        if (dto.getEstado() != null) {
            notificacion.setEstado(dto.getEstado());
            if (dto.getEstado() == EstadoNotificacion.ENVIADA && notificacion.getFechaEnvio() == null) {
                notificacion.setFechaEnvio(LocalDateTime.now());
            }
        }
        return toReadDto(notificacionRepository.save(notificacion));
    }

    public void delete(UUID idNotificacion) {
        notificacionRepository.delete(getEntity(idNotificacion));
    }

    private Notificacion getEntity(UUID idNotificacion) {
        return notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada con id: " + idNotificacion));
    }

    private NotificacionReadDTO toReadDto(Notificacion notificacion) {
        NotificacionReadDTO dto = new NotificacionReadDTO();
        dto.setIdNotificacion(notificacion.getIdNotificacion());
        dto.setIdUsuario(notificacion.getIdUsuario());
        dto.setTitulo(notificacion.getTitulo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setCanal(notificacion.getCanal());
        dto.setEstado(notificacion.getEstado());
        dto.setFechaCreacion(notificacion.getFechaCreacion());
        dto.setFechaEnvio(notificacion.getFechaEnvio());
        return dto;
    }
}

