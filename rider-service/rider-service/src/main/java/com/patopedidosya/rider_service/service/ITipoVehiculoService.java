package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.TipoVehiculoCreateDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoReadDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoUpdateDTO;
import com.patopedidosya.rider_service.model.TipoVehiculo;

import java.util.List;
import java.util.UUID;

public interface ITipoVehiculoService {

    TipoVehiculoReadDTO getTipoVehiculoDto(UUID idTipoVehiculo);

    List<TipoVehiculoReadDTO> getAll();

    TipoVehiculo createTipoVehiculoEntity(TipoVehiculoCreateDTO dto);

    TipoVehiculoReadDTO createTipoVehiculo(TipoVehiculoCreateDTO dto);

    TipoVehiculoReadDTO updateTipoVehiculo(UUID idTipoVehiculo, TipoVehiculoUpdateDTO dto);

    void deleteTipoVehiculo(UUID idTipoVehiculo);

    TipoVehiculo getTipoVehiculoEntity(UUID idTipoVehiculo);

    TipoVehiculoReadDTO patchTipoVehiculo(UUID idTipoVehiculo, TipoVehiculoUpdateDTO dto);
}