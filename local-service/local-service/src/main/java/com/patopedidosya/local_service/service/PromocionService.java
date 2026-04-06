package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.ItemReadDTO;
import com.patopedidosya.local_service.dto.PromocionCreateDTO;
import com.patopedidosya.local_service.dto.PromocionReadDTO;
import com.patopedidosya.local_service.dto.PromocionUpdateDTO;
import com.patopedidosya.local_service.model.Item;
import com.patopedidosya.local_service.model.Promocion;
import com.patopedidosya.local_service.repository.ILocalRepository;
import com.patopedidosya.local_service.repository.IPromocionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PromocionService {

    private final IPromocionRepository promocionRepository;
    private final ILocalRepository localRepository;
    private final ItemService itemService;

    public PromocionService(IPromocionRepository promocionRepository,
                            ILocalRepository localRepository,
                            ItemService itemService) {
        this.promocionRepository = promocionRepository;
        this.localRepository = localRepository;
        this.itemService = itemService;
    }

    public PromocionReadDTO getById(UUID idPromocion) {
        return toReadDto(getEntity(idPromocion));
    }

    public List<PromocionReadDTO> getAll() {
        List<PromocionReadDTO> response = new ArrayList<>();
        for (Promocion promocion : promocionRepository.findAll()) {
            response.add(toReadDto(promocion));
        }
        return response;
    }

    public List<PromocionReadDTO> getByLocal(UUID idLocal) {
        validarLocal(idLocal);
        List<PromocionReadDTO> response = new ArrayList<>();
        for (Promocion promocion : promocionRepository.findByIdLocal(idLocal)) {
            response.add(toReadDto(promocion));
        }
        return response;
    }

    public PromocionReadDTO create(PromocionCreateDTO dto) {
        validarLocal(dto.getIdLocal());
        validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        Item item = itemService.getEntity(dto.getIdItem());
        validarItemPerteneceAlLocal(item, dto.getIdLocal());

        Promocion promocion = new Promocion();
        promocion.setIdLocal(dto.getIdLocal());
        promocion.setIdItem(dto.getIdItem());
        promocion.setCodigo(dto.getCodigo());
        promocion.setDescripcion(dto.getDescripcion());
        promocion.setTipoDescuento(dto.getTipoDescuento());
        promocion.setValor(dto.getValor());
        promocion.setFechaInicio(dto.getFechaInicio());
        promocion.setFechaFin(dto.getFechaFin());
        promocion.setActivo(dto.getActivo());
        promocion.setTopeDescuento(dto.getTopeDescuento());
        return toReadDto(promocionRepository.save(promocion));
    }

    public PromocionReadDTO update(UUID idPromocion, PromocionUpdateDTO dto) {
        Promocion promocion = getEntity(idPromocion);

        if (dto.getIdItem() != null) {
            Item item = itemService.getEntity(dto.getIdItem());
            validarItemPerteneceAlLocal(item, promocion.getIdLocal());
            promocion.setIdItem(dto.getIdItem());
        }

        if (dto.getFechaInicio() != null || dto.getFechaFin() != null) {
            validarFechas(
                    dto.getFechaInicio() != null ? dto.getFechaInicio() : promocion.getFechaInicio(),
                    dto.getFechaFin() != null ? dto.getFechaFin() : promocion.getFechaFin()
            );
        }

        if (dto.getCodigo() != null) promocion.setCodigo(dto.getCodigo());
        if (dto.getDescripcion() != null) promocion.setDescripcion(dto.getDescripcion());
        if (dto.getTipoDescuento() != null) promocion.setTipoDescuento(dto.getTipoDescuento());
        if (dto.getValor() != null) promocion.setValor(dto.getValor());
        if (dto.getFechaInicio() != null) promocion.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) promocion.setFechaFin(dto.getFechaFin());
        if (dto.getActivo() != null) promocion.setActivo(dto.getActivo());
        if (dto.getTopeDescuento() != null) promocion.setTopeDescuento(dto.getTopeDescuento());

        return toReadDto(promocionRepository.save(promocion));
    }

    public void delete(UUID idPromocion) {
        promocionRepository.delete(getEntity(idPromocion));
    }

    private Promocion getEntity(UUID idPromocion) {
        return promocionRepository.findById(idPromocion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Promocion no encontrada con id: " + idPromocion
                ));
    }

    private void validarLocal(UUID idLocal) {
        if (!localRepository.existsById(idLocal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
        }
    }

    private void validarItemPerteneceAlLocal(Item item, UUID idLocal) {
        if (!item.getIdLocal().equals(idLocal)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El item indicado no pertenece al local: " + idLocal
            );
        }
    }

    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (!fechaInicio.isBefore(fechaFin)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de inicio debe ser anterior a la fecha de fin"
            );
        }
    }

    private PromocionReadDTO toReadDto(Promocion promocion) {
        ItemReadDTO item = itemService.getById(promocion.getIdItem());

        PromocionReadDTO dto = new PromocionReadDTO();
        dto.setIdPromocion(promocion.getIdPromocion());
        dto.setIdLocal(promocion.getIdLocal());
        dto.setItem(item);
        dto.setCodigo(promocion.getCodigo());
        dto.setDescripcion(promocion.getDescripcion());
        dto.setTipoDescuento(promocion.getTipoDescuento());
        dto.setValor(promocion.getValor());
        dto.setFechaInicio(promocion.getFechaInicio());
        dto.setFechaFin(promocion.getFechaFin());
        dto.setActivo(promocion.getActivo());
        dto.setTopeDescuento(promocion.getTopeDescuento());
        return dto;
    }
}

