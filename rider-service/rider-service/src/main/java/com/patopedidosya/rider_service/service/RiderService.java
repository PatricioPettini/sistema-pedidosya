package com.patopedidosya.rider_service.service;

import com.patopedidosya.rider_service.dto.RiderCreateDTO;
import com.patopedidosya.rider_service.dto.RiderReadDTO;
import com.patopedidosya.rider_service.dto.RiderUpdateDTO;
import com.patopedidosya.rider_service.dto.TipoVehiculoReadDTO;
import com.patopedidosya.rider_service.mapper.RiderMapper;
import com.patopedidosya.rider_service.model.Rider;
import com.patopedidosya.rider_service.model.TipoVehiculo;
import com.patopedidosya.rider_service.repository.IRiderRepository;
import com.patopedidosya.rider_service.repository.ITipoVehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RiderService implements IRiderService {

    private final RiderMapper riderMapper;
    private final IRiderRepository riderRepository;
    private final ITipoVehiculoRepository tipoVehiculoRepository;

    public RiderService(RiderMapper riderMapper,
                        IRiderRepository riderRepository,
                        ITipoVehiculoRepository tipoVehiculoRepository) {
        this.riderMapper = riderMapper;
        this.riderRepository = riderRepository;
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    @Override
    public RiderReadDTO getRiderDto(UUID idRider) {
        return buildRiderReadDTO(getRiderEntity(idRider));
    }

    @Override
    public List<RiderReadDTO> getAll() {
        List<RiderReadDTO> listaDto = new ArrayList<>();
        for (Rider rider : riderRepository.findAll()) {
            listaDto.add(buildRiderReadDTO(rider));
        }
        return listaDto;
    }

    private Rider getRiderEntity(UUID idRider) {
        return riderRepository.findById(idRider)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rider no encontrado con id: " + idRider
                ));
    }

    private TipoVehiculo getTipoVehiculoEntity(UUID idTipoVehiculo) {
        return tipoVehiculoRepository.findById(idTipoVehiculo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "TipoVehiculo no encontrado con id: " + idTipoVehiculo
                ));
    }

    @Override
    public RiderReadDTO createRider(RiderCreateDTO dto) {
        Rider rider = riderMapper.dtoToEntity(dto);
        rider.setFechaAlta(LocalDate.now());
        if (rider.getActivo() == null) {
            rider.setActivo(Boolean.TRUE);
        }

        if (dto.getIdTipoVehiculo() != null) {
            TipoVehiculo tipoVehiculo = getTipoVehiculoEntity(dto.getIdTipoVehiculo());
            rider.setTipoVehiculo(tipoVehiculo);
        }

        Rider guardado = riderRepository.save(rider);
        return buildRiderReadDTO(guardado);
    }

    @Override
    public RiderReadDTO updateRider(UUID idRider, RiderUpdateDTO dto) {
        Rider rider = getRiderEntity(idRider);
        actualizarCampos(rider, dto);

        Rider actualizado = riderRepository.save(rider);
        return buildRiderReadDTO(actualizado);
    }

    private void actualizarCampos(Rider rider, RiderUpdateDTO dto) {
        if (dto.getNombre() != null) rider.setNombre(dto.getNombre());
        if (dto.getApellido() != null) rider.setApellido(dto.getApellido());
        if (dto.getDni() != null) rider.setDni(dto.getDni());
        if (dto.getTelefono() != null) rider.setTelefono(dto.getTelefono());
        if (dto.getEmail() != null) rider.setEmail(dto.getEmail());
        if (dto.getFechaNacimiento() != null) rider.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getFechaAlta() != null) rider.setFechaAlta(dto.getFechaAlta());
        if (dto.getActivo() != null) rider.setActivo(dto.getActivo());

        if (dto.getIdTipoVehiculo() != null) {
            TipoVehiculo tipoVehiculo = getTipoVehiculoEntity(dto.getIdTipoVehiculo());
            rider.setTipoVehiculo(tipoVehiculo);
        }
    }

    @Override
    public void deleteRider(UUID idRider) {
        Rider rider = getRiderEntity(idRider);
        riderRepository.delete(rider);
    }

    private RiderReadDTO buildRiderReadDTO(Rider rider) {
        RiderReadDTO dto = riderMapper.entityToDto(rider);

        if (rider.getTipoVehiculo() != null) {
            TipoVehiculoReadDTO tipoVehiculoDTO = new TipoVehiculoReadDTO();
            tipoVehiculoDTO.setIdTipoVehiculo(rider.getTipoVehiculo().getIdTipoVehiculo());
            tipoVehiculoDTO.setNombre(rider.getTipoVehiculo().getNombre());
            tipoVehiculoDTO.setActivo(rider.getTipoVehiculo().getActivo());

            dto.setTipoVehiculo(tipoVehiculoDTO);
        }

        return dto;
    }

    @Override
    public RiderReadDTO patchRider(UUID idRider, RiderUpdateDTO dto) {
        Rider rider = getRiderEntity(idRider);
        actualizarCampos(rider, dto);

        Rider actualizado = riderRepository.save(rider);
        return buildRiderReadDTO(actualizado);
    }
}
