package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.TipoVehiculoCreateDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoReadDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoUpdateDTO;
import com.patopedidosya.rider_service.mapper.TipoVehiculoMapper;
import com.patopedidosya.rider_service.model.TipoVehiculo;
import com.patopedidosya.rider_service.repository.ITipoVehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TipoVehiculoService implements ITipoVehiculoService {

    private final TipoVehiculoMapper tipoVehiculoMapper;
    private final ITipoVehiculoRepository tipoVehiculoRepository;

    public TipoVehiculoService(TipoVehiculoMapper tipoVehiculoMapper,
                               ITipoVehiculoRepository tipoVehiculoRepository) {
        this.tipoVehiculoMapper = tipoVehiculoMapper;
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    @Override
    public TipoVehiculoReadDTO getTipoVehiculoDto(UUID idTipoVehiculo) {
        return tipoVehiculoMapper.entityToDto(getTipoVehiculoEntity(idTipoVehiculo));
    }

    @Override
    public List<TipoVehiculoReadDTO> getAll() {
        List<TipoVehiculoReadDTO> listaDto = new ArrayList<>();
        for (TipoVehiculo tipoVehiculo : tipoVehiculoRepository.findAll()) {
            listaDto.add(tipoVehiculoMapper.entityToDto(tipoVehiculo));
        }
        return listaDto;
    }

    @Override
    public TipoVehiculo createTipoVehiculoEntity(TipoVehiculoCreateDTO dto) {
        TipoVehiculo tipoVehiculo = tipoVehiculoMapper.dtoToEntity(dto);
        return tipoVehiculoRepository.save(tipoVehiculo);
    }

    @Override
    public TipoVehiculoReadDTO createTipoVehiculo(TipoVehiculoCreateDTO dto) {
        TipoVehiculo guardado = createTipoVehiculoEntity(dto);
        return tipoVehiculoMapper.entityToDto(guardado);
    }

    @Override
    public TipoVehiculoReadDTO updateTipoVehiculo(UUID idTipoVehiculo, TipoVehiculoUpdateDTO dto) {
        TipoVehiculo tipoVehiculo = getTipoVehiculoEntity(idTipoVehiculo);
        actualizarCampos(tipoVehiculo, dto);

        TipoVehiculo actualizado = tipoVehiculoRepository.save(tipoVehiculo);
        return tipoVehiculoMapper.entityToDto(actualizado);
    }

    private void actualizarCampos(TipoVehiculo tipoVehiculo, TipoVehiculoUpdateDTO dto) {
        if (dto.getNombre() != null) tipoVehiculo.setNombre(dto.getNombre());
        if (dto.getActivo() != null) tipoVehiculo.setActivo(dto.getActivo());
    }

    @Override
    public void deleteTipoVehiculo(UUID idTipoVehiculo) {
        TipoVehiculo tipoVehiculo = getTipoVehiculoEntity(idTipoVehiculo);
        tipoVehiculoRepository.delete(tipoVehiculo);
    }

    @Override
    public TipoVehiculo getTipoVehiculoEntity(UUID idTipoVehiculo) {
        return tipoVehiculoRepository.findById(idTipoVehiculo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "TipoVehiculo no encontrado con id: " + idTipoVehiculo
                ));
    }

    @Override
    public TipoVehiculoReadDTO patchTipoVehiculo(UUID idTipoVehiculo, TipoVehiculoUpdateDTO dto) {
        TipoVehiculo tipoVehiculo = getTipoVehiculoEntity(idTipoVehiculo);
        actualizarCampos(tipoVehiculo, dto);

        TipoVehiculo actualizado = tipoVehiculoRepository.save(tipoVehiculo);
        return tipoVehiculoMapper.entityToDto(actualizado);
    }
}