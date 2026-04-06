package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.CategoriaItemCreateDTO;
import com.patopedidosya.local_service.dto.CategoriaItemReadDTO;
import com.patopedidosya.local_service.dto.CategoriaItemUpdateDTO;
import com.patopedidosya.local_service.model.CategoriaItem;
import com.patopedidosya.local_service.repository.ICategoriaItemRepository;
import com.patopedidosya.local_service.repository.ILocalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriaItemService {

    private final ICategoriaItemRepository categoriaItemRepository;
    private final ILocalRepository localRepository;

    public CategoriaItemService(ICategoriaItemRepository categoriaItemRepository, ILocalRepository localRepository) {
        this.categoriaItemRepository = categoriaItemRepository;
        this.localRepository = localRepository;
    }

    public CategoriaItemReadDTO getById(UUID idCategoriaItem) {
        return toReadDto(getEntity(idCategoriaItem));
    }

    public List<CategoriaItemReadDTO> getAll() {
        List<CategoriaItemReadDTO> response = new ArrayList<>();
        for (CategoriaItem categoriaItem : categoriaItemRepository.findAll()) {
            response.add(toReadDto(categoriaItem));
        }
        return response;
    }

    public List<CategoriaItemReadDTO> getByLocal(UUID idLocal) {
        validarLocal(idLocal);
        List<CategoriaItemReadDTO> response = new ArrayList<>();
        for (CategoriaItem categoriaItem : categoriaItemRepository.findByIdLocal(idLocal)) {
            response.add(toReadDto(categoriaItem));
        }
        return response;
    }

    public CategoriaItemReadDTO create(CategoriaItemCreateDTO dto) {
        validarLocal(dto.getIdLocal());

        CategoriaItem categoriaItem = new CategoriaItem();
        categoriaItem.setIdLocal(dto.getIdLocal());
        categoriaItem.setNombre(dto.getNombre());
        categoriaItem.setDescripcion(dto.getDescripcion());
        categoriaItem.setActivo(dto.getActivo());
        categoriaItem.setOrden(dto.getOrden());
        return toReadDto(categoriaItemRepository.save(categoriaItem));
    }

    public CategoriaItemReadDTO update(UUID idCategoriaItem, CategoriaItemUpdateDTO dto) {
        CategoriaItem categoriaItem = getEntity(idCategoriaItem);
        if (dto.getNombre() != null) categoriaItem.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) categoriaItem.setDescripcion(dto.getDescripcion());
        if (dto.getActivo() != null) categoriaItem.setActivo(dto.getActivo());
        if (dto.getOrden() != null) categoriaItem.setOrden(dto.getOrden());
        return toReadDto(categoriaItemRepository.save(categoriaItem));
    }

    public void delete(UUID idCategoriaItem) {
        categoriaItemRepository.delete(getEntity(idCategoriaItem));
    }

    public CategoriaItem getEntity(UUID idCategoriaItem) {
        return categoriaItemRepository.findById(idCategoriaItem)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria item no encontrada con id: " + idCategoriaItem
                ));
    }

    private void validarLocal(UUID idLocal) {
        if (!localRepository.existsById(idLocal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
        }
    }

    private CategoriaItemReadDTO toReadDto(CategoriaItem categoriaItem) {
        CategoriaItemReadDTO dto = new CategoriaItemReadDTO();
        dto.setIdCategoriaItem(categoriaItem.getIdCategoriaItem());
        dto.setIdLocal(categoriaItem.getIdLocal());
        dto.setNombre(categoriaItem.getNombre());
        dto.setDescripcion(categoriaItem.getDescripcion());
        dto.setActivo(categoriaItem.getActivo());
        dto.setOrden(categoriaItem.getOrden());
        return dto;
    }
}
