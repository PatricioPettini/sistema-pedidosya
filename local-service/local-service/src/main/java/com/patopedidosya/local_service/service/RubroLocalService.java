package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.RubroLocalCreateDTO;
import com.patopedidosya.local_service.dto.RubroLocalReadDTO;
import com.patopedidosya.local_service.dto.RubroLocalUpdateDTO;
import com.patopedidosya.local_service.model.RubroLocal;
import com.patopedidosya.local_service.repository.IRubroLocalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RubroLocalService {

    private final IRubroLocalRepository rubroLocalRepository;

    public RubroLocalService(IRubroLocalRepository rubroLocalRepository) {
        this.rubroLocalRepository = rubroLocalRepository;
    }

    public RubroLocalReadDTO getById(UUID idRubroLocal) {
        return toReadDto(getEntity(idRubroLocal));
    }

    public List<RubroLocalReadDTO> getAll() {
        List<RubroLocalReadDTO> response = new ArrayList<>();
        for (RubroLocal rubroLocal : rubroLocalRepository.findAll()) {
            response.add(toReadDto(rubroLocal));
        }
        return response;
    }

    public RubroLocalReadDTO create(RubroLocalCreateDTO dto) {
        RubroLocal rubroLocal = new RubroLocal();
        rubroLocal.setNombre(dto.getNombre());
        rubroLocal.setDescripcion(dto.getDescripcion());
        rubroLocal.setActivo(dto.getActivo());
        return toReadDto(rubroLocalRepository.save(rubroLocal));
    }

    public RubroLocalReadDTO update(UUID idRubroLocal, RubroLocalUpdateDTO dto) {
        RubroLocal rubroLocal = getEntity(idRubroLocal);
        if (dto.getNombre() != null) rubroLocal.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) rubroLocal.setDescripcion(dto.getDescripcion());
        if (dto.getActivo() != null) rubroLocal.setActivo(dto.getActivo());
        return toReadDto(rubroLocalRepository.save(rubroLocal));
    }

    public void delete(UUID idRubroLocal) {
        rubroLocalRepository.delete(getEntity(idRubroLocal));
    }

    public RubroLocal getEntity(UUID idRubroLocal) {
        return rubroLocalRepository.findById(idRubroLocal)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rubro local no encontrado con id: " + idRubroLocal
                ));
    }

    private RubroLocalReadDTO toReadDto(RubroLocal rubroLocal) {
        RubroLocalReadDTO dto = new RubroLocalReadDTO();
        dto.setIdRubroLocal(rubroLocal.getIdRubroLocal());
        dto.setNombre(rubroLocal.getNombre());
        dto.setDescripcion(rubroLocal.getDescripcion());
        dto.setActivo(rubroLocal.getActivo());
        return dto;
    }
}

