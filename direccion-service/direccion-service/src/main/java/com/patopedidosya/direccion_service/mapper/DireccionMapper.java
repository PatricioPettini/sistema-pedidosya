package com.patopedidosya.direccion_service.mapper;

import com.patopedidosya.direccion_service.dto.DireccionCreateDTO;
import com.patopedidosya.direccion_service.dto.DireccionReadDTO;
import com.patopedidosya.direccion_service.model.Direccion;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    private final ModelMapper modelMapper;

    public DireccionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DireccionReadDTO entityToDto(Direccion direccion){
        return modelMapper.map(direccion, DireccionReadDTO.class);
    }

    public Direccion dtoToEntity(DireccionCreateDTO dto){
        return modelMapper.map(dto, Direccion.class);
    }
}
