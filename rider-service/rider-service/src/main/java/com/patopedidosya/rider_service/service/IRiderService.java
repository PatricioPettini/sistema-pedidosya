package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.RiderCreateDTO;
import com.patopedidosya.rider_service.dto.RiderReadDTO;
import com.patopedidosya.rider_service.dto.RiderUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface IRiderService {
    RiderReadDTO getRiderDto(UUID idRider);
    List<RiderReadDTO> getAll();
    RiderReadDTO createRider(RiderCreateDTO dto);
    RiderReadDTO updateRider(UUID idRider, RiderUpdateDTO dto);
    void deleteRider(UUID idRider);
    RiderReadDTO patchRider(UUID idRider, RiderUpdateDTO dto);
}
