package com.patopedidosya.direccion_service.service;

import com.patopedidosya.direccion_service.dto.DireccionCreateDTO;
import com.patopedidosya.direccion_service.dto.DireccionReadDTO;
import com.patopedidosya.direccion_service.dto.DireccionUpdateDTO;
import com.patopedidosya.direccion_service.model.Direccion;

import java.util.UUID;

public interface IDireccionService {
    public DireccionReadDTO getDireccionDto(UUID id);
    public DireccionReadDTO createDireccion(DireccionCreateDTO dto);
    public DireccionReadDTO updateDireccion(UUID idDireccion, DireccionUpdateDTO dto);
    public void deleteDireccion(UUID id);
}
