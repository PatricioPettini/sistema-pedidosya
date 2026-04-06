package com.patopedidosya.rider_service.mapper;

import com.patopedidosya.rider_service.dto.TipoVehiculoCreateDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoReadDTO;
import com.patopedidosya.rider_service.model.Rider;
import com.patopedidosya.rider_service.model.TipoVehiculo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TipoVehiculoMapper {

    private final ModelMapper modelMapper;

    public TipoVehiculoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TipoVehiculoReadDTO entityToDto(TipoVehiculo tipoVehiculo){
        return modelMapper.map(tipoVehiculo, TipoVehiculoReadDTO.class);
    }

    public TipoVehiculo dtoToEntity(TipoVehiculoCreateDTO dto){
        return modelMapper.map(dto, TipoVehiculo.class);
    }
}
