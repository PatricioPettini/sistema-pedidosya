package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.ItemCreateDTO;
import com.patopedidosya.local_service.dto.ItemReadDTO;
import com.patopedidosya.local_service.dto.ItemUpdateDTO;
import com.patopedidosya.local_service.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{idItem}")
    public ResponseEntity<ItemReadDTO> getById(@PathVariable UUID idItem) {
        return ResponseEntity.ok(itemService.getById(idItem));
    }

    @GetMapping
    public ResponseEntity<List<ItemReadDTO>> getAll(@RequestParam(required = false) UUID idLocal) {
        if (idLocal != null) {
            return ResponseEntity.ok(itemService.getByLocal(idLocal));
        }
        return ResponseEntity.ok(itemService.getAll());
    }

    @PostMapping
    public ResponseEntity<ItemReadDTO> create(@Valid @RequestBody ItemCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(dto));
    }

    @PutMapping("/{idItem}")
    public ResponseEntity<ItemReadDTO> update(@PathVariable UUID idItem,
                                              @Valid @RequestBody ItemUpdateDTO dto) {
        return ResponseEntity.ok(itemService.update(idItem, dto));
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> delete(@PathVariable UUID idItem) {
        itemService.delete(idItem);
        return ResponseEntity.noContent().build();
    }
}

