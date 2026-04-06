package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.DireccionReadDTO;
import com.patopedidosya.local_service.dto.LocalCreateDTO;
import com.patopedidosya.local_service.dto.LocalReadDTO;
import com.patopedidosya.local_service.dto.LocalUpdateDTO;
import com.patopedidosya.local_service.dto.RubroLocalReadDTO;
import com.patopedidosya.local_service.mapper.LocalMapper;
import com.patopedidosya.local_service.model.LocalEntity;
import com.patopedidosya.local_service.model.RubroLocal;
import com.patopedidosya.local_service.repository.DireccionAPI;
import com.patopedidosya.local_service.repository.ILocalRepository;
import com.patopedidosya.local_service.repository.IRubroLocalRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocalService implements ILocalService {

    private final LocalMapper localMapper;
    private final ILocalRepository localRepository;
    private final IRubroLocalRepository rubroLocalRepository;
    private final DireccionAPI direccionAPI;

    public LocalService(LocalMapper localMapper,
                        ILocalRepository localRepository,
                        IRubroLocalRepository rubroLocalRepository,
                        DireccionAPI direccionAPI) {
        this.localMapper = localMapper;
        this.localRepository = localRepository;
        this.rubroLocalRepository = rubroLocalRepository;
        this.direccionAPI = direccionAPI;
    }

    @Override
    public LocalReadDTO getLocalDto(UUID idLocal) {
        return buildLocalReadDTO(getLocalEntity(idLocal));
    }

    @Override
    public List<LocalReadDTO> getAll() {
        List<LocalReadDTO> listaDto = new ArrayList<>();
        for (LocalEntity local : localRepository.findAll()) {
            listaDto.add(buildLocalReadDTO(local));
        }
        return listaDto;
    }

    private LocalEntity getLocalEntity(UUID id) {
        return localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Local no encontrado con id: " + id
                ));
    }

    private RubroLocal getRubroLocalEntity(UUID idRubroLocal) {
        return rubroLocalRepository.findById(idRubroLocal)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Rubro local no encontrado con id: " + idRubroLocal
                ));
    }

    @Override
    public LocalReadDTO createLocal(LocalCreateDTO dto) {
        DireccionReadDTO direccion = getDireccion(dto.getIdDireccion());
        RubroLocal rubroLocal = getRubroLocalEntity(dto.getIdRubroLocal());

        LocalEntity local = localMapper.dtoToEntity(dto);
        if (local.getActivo() == null) {
            local.setActivo(Boolean.TRUE);
        }

        LocalEntity guardado = localRepository.save(local);
        LocalReadDTO nuevo = localMapper.entityToDto(guardado);
        nuevo.setDireccion(direccion);
        nuevo.setRubroLocal(buildRubroLocalReadDTO(rubroLocal));
        return nuevo;
    }

    @Override
    public LocalReadDTO updateLocal(UUID idLocal, LocalUpdateDTO dto) {
        LocalEntity local = getLocalEntity(idLocal);
        actualizarCampos(local, dto);

        LocalEntity actualizado = localRepository.save(local);
        return buildLocalReadDTO(actualizado);
    }

    private void actualizarCampos(LocalEntity local, LocalUpdateDTO dto) {
        if (dto.getEmail() != null) local.setEmail(dto.getEmail());
        if (dto.getNombre() != null) local.setNombre(dto.getNombre());
        if (dto.getTelefono() != null) local.setTelefono(dto.getTelefono());
        if (dto.getActivo() != null) local.setActivo(dto.getActivo());

        if (dto.getIdDireccion() != null) {
            getDireccion(dto.getIdDireccion());
            local.setIdDireccion(dto.getIdDireccion());
        }

        if (dto.getIdRubroLocal() != null) {
            getRubroLocalEntity(dto.getIdRubroLocal());
            local.setIdRubroLocal(dto.getIdRubroLocal());
        }
    }

    @Override
    public void deleteLocal(UUID id) {
        LocalEntity local = getLocalEntity(id);
        localRepository.delete(local);
    }

    private LocalReadDTO buildLocalReadDTO(LocalEntity entity) {
        LocalReadDTO dto = localMapper.entityToDto(entity);
        dto.setDireccion(getDireccion(entity.getIdDireccion()));
        dto.setRubroLocal(buildRubroLocalReadDTO(getRubroLocalEntity(entity.getIdRubroLocal())));
        return dto;
    }

    private RubroLocalReadDTO buildRubroLocalReadDTO(RubroLocal rubroLocal) {
        RubroLocalReadDTO dto = new RubroLocalReadDTO();
        dto.setIdRubroLocal(rubroLocal.getIdRubroLocal());
        dto.setNombre(rubroLocal.getNombre());
        dto.setDescripcion(rubroLocal.getDescripcion());
        dto.setActivo(rubroLocal.getActivo());
        return dto;
    }

    private DireccionReadDTO getDireccion(UUID idDireccion) {
        try {
            ResponseEntity<DireccionReadDTO> response = direccionAPI.getDireccion(idDireccion);

            if (response != null && response.getBody() != null) {
                return response.getBody();
            }

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La direccion indicada no existe: " + idDireccion
            );

        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La direccion indicada no existe: " + idDireccion
            );
        }
    }
}
