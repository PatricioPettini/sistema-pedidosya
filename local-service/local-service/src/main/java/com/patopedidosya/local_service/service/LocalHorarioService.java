package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.LocalHorarioCreateDTO;
import com.patopedidosya.local_service.dto.LocalHorarioReadDTO;
import com.patopedidosya.local_service.dto.LocalHorarioUpdateDTO;
import com.patopedidosya.local_service.model.LocalHorario;
import com.patopedidosya.local_service.repository.ILocalHorarioRepository;
import com.patopedidosya.local_service.repository.ILocalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocalHorarioService {

    private final ILocalHorarioRepository localHorarioRepository;
    private final ILocalRepository localRepository;

    public LocalHorarioService(ILocalHorarioRepository localHorarioRepository, ILocalRepository localRepository) {
        this.localHorarioRepository = localHorarioRepository;
        this.localRepository = localRepository;
    }

    public LocalHorarioReadDTO getById(UUID idLocalHorario) {
        return toReadDto(getEntity(idLocalHorario));
    }

    public List<LocalHorarioReadDTO> getAll() {
        List<LocalHorarioReadDTO> response = new ArrayList<>();
        for (LocalHorario localHorario : localHorarioRepository.findAll()) {
            response.add(toReadDto(localHorario));
        }
        return response;
    }

    public List<LocalHorarioReadDTO> getByLocal(UUID idLocal) {
        validarLocal(idLocal);
        List<LocalHorarioReadDTO> response = new ArrayList<>();
        for (LocalHorario localHorario : localHorarioRepository.findByIdLocal(idLocal)) {
            response.add(toReadDto(localHorario));
        }
        return response;
    }

    public LocalHorarioReadDTO create(LocalHorarioCreateDTO dto) {
        validarLocal(dto.getIdLocal());
        validarHorario(dto.getHoraApertura(), dto.getHoraCierre());

        LocalHorario localHorario = new LocalHorario();
        localHorario.setIdLocal(dto.getIdLocal());
        localHorario.setDiaSemana(dto.getDiaSemana());
        localHorario.setHoraApertura(dto.getHoraApertura());
        localHorario.setHoraCierre(dto.getHoraCierre());
        localHorario.setAbierto(dto.getAbierto());
        return toReadDto(localHorarioRepository.save(localHorario));
    }

    public LocalHorarioReadDTO update(UUID idLocalHorario, LocalHorarioUpdateDTO dto) {
        LocalHorario localHorario = getEntity(idLocalHorario);

        if (dto.getHoraApertura() != null || dto.getHoraCierre() != null) {
            validarHorario(
                    dto.getHoraApertura() != null ? dto.getHoraApertura() : localHorario.getHoraApertura(),
                    dto.getHoraCierre() != null ? dto.getHoraCierre() : localHorario.getHoraCierre()
            );
        }

        if (dto.getDiaSemana() != null) localHorario.setDiaSemana(dto.getDiaSemana());
        if (dto.getHoraApertura() != null) localHorario.setHoraApertura(dto.getHoraApertura());
        if (dto.getHoraCierre() != null) localHorario.setHoraCierre(dto.getHoraCierre());
        if (dto.getAbierto() != null) localHorario.setAbierto(dto.getAbierto());

        return toReadDto(localHorarioRepository.save(localHorario));
    }

    public void delete(UUID idLocalHorario) {
        localHorarioRepository.delete(getEntity(idLocalHorario));
    }

    private LocalHorario getEntity(UUID idLocalHorario) {
        return localHorarioRepository.findById(idLocalHorario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Horario de local no encontrado con id: " + idLocalHorario
                ));
    }

    private void validarLocal(UUID idLocal) {
        if (!localRepository.existsById(idLocal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
        }
    }

    private void validarHorario(LocalTime horaApertura, LocalTime horaCierre) {
        if (horaApertura.equals(horaCierre)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La hora de apertura y cierre no pueden ser iguales"
            );
        }
    }

    private LocalHorarioReadDTO toReadDto(LocalHorario localHorario) {
        LocalHorarioReadDTO dto = new LocalHorarioReadDTO();
        dto.setIdLocalHorario(localHorario.getIdLocalHorario());
        dto.setIdLocal(localHorario.getIdLocal());
        dto.setDiaSemana(localHorario.getDiaSemana());
        dto.setHoraApertura(localHorario.getHoraApertura());
        dto.setHoraCierre(localHorario.getHoraCierre());
        dto.setAbierto(localHorario.getAbierto());
        return dto;
    }
}
