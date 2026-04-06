package com.patopedidosya.direccion_service.service;

import com.patopedidosya.direccion_service.dto.DireccionCreateDTO;
import com.patopedidosya.direccion_service.dto.DireccionReadDTO;
import com.patopedidosya.direccion_service.dto.DireccionUpdateDTO;
import com.patopedidosya.direccion_service.mapper.DireccionMapper;
import com.patopedidosya.direccion_service.model.Direccion;
import com.patopedidosya.direccion_service.repository.IDireccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DireccionService implements IDireccionService {

    private final IDireccionRepository direccionRepository;
    private final DireccionMapper direccionMapper;

    public DireccionService(IDireccionRepository direccionRepository, DireccionMapper direccionMapper) {
        this.direccionRepository = direccionRepository;
        this.direccionMapper = direccionMapper;
    }

    @Override
    public DireccionReadDTO getDireccionDto(UUID id) {
        return direccionMapper.entityToDto(getDireccionEntity(id));
    }

    private Direccion getDireccionEntity(UUID id) {
        return direccionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Direccion no encontrada con id: " + id
        ));
    }

    @Override
    public DireccionReadDTO createDireccion(DireccionCreateDTO dto) {
        Direccion direccion = direccionMapper.dtoToEntity(dto);
        Direccion guardada = direccionRepository.save(direccion);
        return direccionMapper.entityToDto(guardada);
    }

    @Override
    public DireccionReadDTO updateDireccion(UUID idDireccion, DireccionUpdateDTO dto) {
        Direccion direccion = getDireccionEntity(idDireccion);
        actualizarCampos(direccion, dto);
        return direccionMapper.entityToDto(direccionRepository.save(direccion));
    }

    private void actualizarCampos(Direccion direccion, DireccionUpdateDTO dto) {
        if (dto.getCalle() != null) direccion.setCalle(dto.getCalle());
        if (dto.getNumero() != null) direccion.setNumero(dto.getNumero());
        if (dto.getPiso() != null) direccion.setPiso(dto.getPiso());
        if (dto.getDepto() != null) direccion.setDepto(dto.getDepto());
        if (dto.getCiudad() != null) direccion.setCiudad(dto.getCiudad());
        if (dto.getProvincia() != null) direccion.setProvincia(dto.getProvincia());
        if (dto.getReferencia() != null) direccion.setReferencia(dto.getReferencia());
        if (dto.getCodigoPostal() != null) direccion.setCodigoPostal(dto.getCodigoPostal());
    }

    @Override
    public void deleteDireccion(UUID id) {
        Direccion direccion = getDireccionEntity(id);
        direccionRepository.delete(direccion);
    }
}
