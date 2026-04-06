package com.patopedidosya.user_service.service;

import com.patopedidosya.user_service.dto.DireccionReadDTO;
import com.patopedidosya.user_service.dto.UserDireccionCreateDTO;
import com.patopedidosya.user_service.dto.UserDireccionReadDTO;
import com.patopedidosya.user_service.dto.UserDireccionUpdateDTO;
import com.patopedidosya.user_service.model.UserDireccion;
import com.patopedidosya.user_service.repository.DireccionAPI;
import com.patopedidosya.user_service.repository.IUserDireccionRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserDireccionService {

    private final IUserDireccionRepository userDireccionRepository;
    private final UsuarioService usuarioService;
    private final DireccionAPI direccionAPI;

    public UserDireccionService(IUserDireccionRepository userDireccionRepository,
                                UsuarioService usuarioService,
                                DireccionAPI direccionAPI) {
        this.userDireccionRepository = userDireccionRepository;
        this.usuarioService = usuarioService;
        this.direccionAPI = direccionAPI;
    }

    public UserDireccionReadDTO getById(UUID idUserDireccion) {
        return toReadDto(getEntity(idUserDireccion));
    }

    public List<UserDireccionReadDTO> getByUsuario(UUID idUsuario) {
        usuarioService.getEntity(idUsuario);
        List<UserDireccionReadDTO> response = new ArrayList<>();
        for (UserDireccion userDireccion : userDireccionRepository.findByIdUsuario(idUsuario)) {
            response.add(toReadDto(userDireccion));
        }
        return response;
    }

    public UserDireccionReadDTO create(UserDireccionCreateDTO dto) {
        usuarioService.getEntity(dto.getIdUsuario());
        getDireccion(dto.getIdDireccion());

        if (Boolean.TRUE.equals(dto.getEsPrincipal())) {
            desmarcarPrincipal(dto.getIdUsuario());
        }

        UserDireccion userDireccion = new UserDireccion();
        userDireccion.setIdUsuario(dto.getIdUsuario());
        userDireccion.setIdDireccion(dto.getIdDireccion());
        userDireccion.setAlias(dto.getAlias());
        userDireccion.setEsPrincipal(dto.getEsPrincipal());
        return toReadDto(userDireccionRepository.save(userDireccion));
    }

    public UserDireccionReadDTO update(UUID idUserDireccion, UserDireccionUpdateDTO dto) {
        UserDireccion userDireccion = getEntity(idUserDireccion);

        if (dto.getIdDireccion() != null) {
            getDireccion(dto.getIdDireccion());
            userDireccion.setIdDireccion(dto.getIdDireccion());
        }
        if (dto.getAlias() != null) userDireccion.setAlias(dto.getAlias());
        if (dto.getEsPrincipal() != null) {
            if (dto.getEsPrincipal()) {
                desmarcarPrincipal(userDireccion.getIdUsuario());
            }
            userDireccion.setEsPrincipal(dto.getEsPrincipal());
        }

        return toReadDto(userDireccionRepository.save(userDireccion));
    }

    public void delete(UUID idUserDireccion) {
        userDireccionRepository.delete(getEntity(idUserDireccion));
    }

    private void desmarcarPrincipal(UUID idUsuario) {
        List<UserDireccion> direcciones = userDireccionRepository.findByIdUsuario(idUsuario);
        for (UserDireccion direccion : direcciones) {
            if (Boolean.TRUE.equals(direccion.getEsPrincipal())) {
                direccion.setEsPrincipal(Boolean.FALSE);
                userDireccionRepository.save(direccion);
            }
        }
    }

    private UserDireccion getEntity(UUID idUserDireccion) {
        return userDireccionRepository.findById(idUserDireccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Direccion de usuario no encontrada con id: " + idUserDireccion
                ));
    }

    private DireccionReadDTO getDireccion(UUID idDireccion) {
        try {
            ResponseEntity<DireccionReadDTO> response = direccionAPI.getDireccion(idDireccion);
            if (response != null && response.getBody() != null) {
                return response.getBody();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La direccion indicada no existe: " + idDireccion);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La direccion indicada no existe: " + idDireccion);
        }
    }

    private UserDireccionReadDTO toReadDto(UserDireccion userDireccion) {
        UserDireccionReadDTO dto = new UserDireccionReadDTO();
        dto.setIdUserDireccion(userDireccion.getIdUserDireccion());
        dto.setIdUsuario(userDireccion.getIdUsuario());
        dto.setDireccion(getDireccion(userDireccion.getIdDireccion()));
        dto.setAlias(userDireccion.getAlias());
        dto.setEsPrincipal(userDireccion.getEsPrincipal());
        return dto;
    }
}
