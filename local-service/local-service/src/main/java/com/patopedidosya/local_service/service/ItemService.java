package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.CategoriaItemReadDTO;
import com.patopedidosya.local_service.dto.ItemCreateDTO;
import com.patopedidosya.local_service.dto.ItemReadDTO;
import com.patopedidosya.local_service.dto.ItemUpdateDTO;
import com.patopedidosya.local_service.model.CategoriaItem;
import com.patopedidosya.local_service.model.Item;
import com.patopedidosya.local_service.repository.ICategoriaItemRepository;
import com.patopedidosya.local_service.repository.IItemRepository;
import com.patopedidosya.local_service.repository.ILocalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final IItemRepository itemRepository;
    private final ICategoriaItemRepository categoriaItemRepository;
    private final ILocalRepository localRepository;

    public ItemService(IItemRepository itemRepository,
                       ICategoriaItemRepository categoriaItemRepository,
                       ILocalRepository localRepository) {
        this.itemRepository = itemRepository;
        this.categoriaItemRepository = categoriaItemRepository;
        this.localRepository = localRepository;
    }

    public ItemReadDTO getById(UUID idItem) {
        return toReadDto(getEntity(idItem));
    }

    public List<ItemReadDTO> getAll() {
        List<ItemReadDTO> response = new ArrayList<>();
        for (Item item : itemRepository.findAll()) {
            response.add(toReadDto(item));
        }
        return response;
    }

    public List<ItemReadDTO> getByLocal(UUID idLocal) {
        validarLocal(idLocal);
        List<ItemReadDTO> response = new ArrayList<>();
        for (Item item : itemRepository.findByIdLocal(idLocal)) {
            response.add(toReadDto(item));
        }
        return response;
    }

    public ItemReadDTO create(ItemCreateDTO dto) {
        validarLocal(dto.getIdLocal());
        CategoriaItem categoriaItem = getCategoriaEntity(dto.getIdCategoriaItem());
        validarCategoriaPerteneceAlLocal(categoriaItem, dto.getIdLocal());

        Item item = new Item();
        item.setIdLocal(dto.getIdLocal());
        item.setIdCategoriaItem(dto.getIdCategoriaItem());
        item.setNombre(dto.getNombre());
        item.setDescripcion(dto.getDescripcion());
        item.setPrecio(dto.getPrecio());
        item.setDisponible(dto.getDisponible());
        item.setImagenUrl(dto.getImagenUrl());
        return toReadDto(itemRepository.save(item));
    }

    public ItemReadDTO update(UUID idItem, ItemUpdateDTO dto) {
        Item item = getEntity(idItem);

        if (dto.getIdCategoriaItem() != null) {
            CategoriaItem categoriaItem = getCategoriaEntity(dto.getIdCategoriaItem());
            validarCategoriaPerteneceAlLocal(categoriaItem, item.getIdLocal());
            item.setIdCategoriaItem(dto.getIdCategoriaItem());
        }

        if (dto.getNombre() != null) item.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) item.setDescripcion(dto.getDescripcion());
        if (dto.getPrecio() != null) item.setPrecio(dto.getPrecio());
        if (dto.getDisponible() != null) item.setDisponible(dto.getDisponible());
        if (dto.getImagenUrl() != null) item.setImagenUrl(dto.getImagenUrl());

        return toReadDto(itemRepository.save(item));
    }

    public void delete(UUID idItem) {
        itemRepository.delete(getEntity(idItem));
    }

    public Item getEntity(UUID idItem) {
        return itemRepository.findById(idItem)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Item no encontrado con id: " + idItem
                ));
    }

    private CategoriaItem getCategoriaEntity(UUID idCategoriaItem) {
        return categoriaItemRepository.findById(idCategoriaItem)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Categoria item no encontrada con id: " + idCategoriaItem
                ));
    }

    private void validarLocal(UUID idLocal) {
        if (!localRepository.existsById(idLocal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
        }
    }

    private void validarCategoriaPerteneceAlLocal(CategoriaItem categoriaItem, UUID idLocal) {
        if (!categoriaItem.getIdLocal().equals(idLocal)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La categoria indicada no pertenece al local: " + idLocal
            );
        }
    }

    private ItemReadDTO toReadDto(Item item) {
        CategoriaItem categoriaItem = getCategoriaEntity(item.getIdCategoriaItem());
        CategoriaItemReadDTO categoriaItemReadDTO = new CategoriaItemReadDTO();
        categoriaItemReadDTO.setIdCategoriaItem(categoriaItem.getIdCategoriaItem());
        categoriaItemReadDTO.setIdLocal(categoriaItem.getIdLocal());
        categoriaItemReadDTO.setNombre(categoriaItem.getNombre());
        categoriaItemReadDTO.setDescripcion(categoriaItem.getDescripcion());
        categoriaItemReadDTO.setActivo(categoriaItem.getActivo());
        categoriaItemReadDTO.setOrden(categoriaItem.getOrden());

        ItemReadDTO dto = new ItemReadDTO();
        dto.setIdItem(item.getIdItem());
        dto.setIdLocal(item.getIdLocal());
        dto.setCategoriaItem(categoriaItemReadDTO);
        dto.setNombre(item.getNombre());
        dto.setDescripcion(item.getDescripcion());
        dto.setPrecio(item.getPrecio());
        dto.setDisponible(item.getDisponible());
        dto.setImagenUrl(item.getImagenUrl());
        return dto;
    }
}

