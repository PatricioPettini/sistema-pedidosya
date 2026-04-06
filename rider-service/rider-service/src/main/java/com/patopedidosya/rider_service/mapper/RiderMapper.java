package com.patopedidosya.rider_service.mapper;

import com.patopedidosya.rider_service.dto.RiderCreateDTO;
import com.patopedidosya.rider_service.dto.RiderReadDTO;
import com.patopedidosya.rider_service.model.Rider;
import org.springframework.stereotype.Component;

@Component
public class RiderMapper {

    public RiderReadDTO entityToDto(Rider rider) {
        RiderReadDTO dto = new RiderReadDTO();
        dto.setIdRider(rider.getIdRider());
        dto.setNombre(rider.getNombre());
        dto.setApellido(rider.getApellido());
        dto.setDni(rider.getDni());
        dto.setTelefono(rider.getTelefono());
        dto.setEmail(rider.getEmail());
        dto.setFechaNacimiento(rider.getFechaNacimiento());
        dto.setFechaAlta(rider.getFechaAlta());
        dto.setActivo(rider.getActivo());
        return dto;
    }

    public Rider dtoToEntity(RiderCreateDTO dto) {
        Rider rider = new Rider();
        rider.setNombre(dto.getNombre());
        rider.setApellido(dto.getApellido());
        rider.setDni(dto.getDni());
        rider.setTelefono(dto.getTelefono());
        rider.setEmail(dto.getEmail());
        rider.setFechaNacimiento(dto.getFechaNacimiento());
        rider.setActivo(dto.getActivo());
        return rider;
    }
}
