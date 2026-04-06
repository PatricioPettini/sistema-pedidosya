package com.patopedidosya.local_service.mapper;

import com.patopedidosya.local_service.dto.LocalCreateDTO;
import com.patopedidosya.local_service.dto.LocalReadDTO;
import com.patopedidosya.local_service.model.LocalEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LocalMapper {

    private final ModelMapper modelMapper;

    public LocalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LocalReadDTO entityToDto(LocalEntity local) {
        return modelMapper.map(local, LocalReadDTO.class);
    }

    public LocalEntity dtoToEntity(LocalCreateDTO dto) {
        LocalEntity entity = modelMapper.map(dto, LocalEntity.class);
        entity.setIdLocal(null);
        return entity;
    }
}
