package com.patopedidosya.local_service.controller;

import com.patopedidosya.local_service.dto.CategoriaItemCreateDTO;
import com.patopedidosya.local_service.dto.CategoriaItemReadDTO;
import com.patopedidosya.local_service.dto.CategoriaItemUpdateDTO;
import com.patopedidosya.local_service.service.CategoriaItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias-item")
public class CategoriaItemController {

    private final CategoriaItemService categoriaItemService;

    public CategoriaItemController(CategoriaItemService categoriaItemService) {
        this.categoriaItemService = categoriaItemService;
    }

    @GetMapping("/{idCategoriaItem}")
    public ResponseEntity<CategoriaItemReadDTO> getById(@PathVariable UUID idCategoriaItem) {
        return ResponseEntity.ok(categoriaItemService.getById(idCategoriaItem));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaItemReadDTO>> getAll(@RequestParam(required = false) UUID idLocal) {
        if (idLocal != null) {
            return ResponseEntity.ok(categoriaItemService.getByLocal(idLocal));
        }
        return ResponseEntity.ok(categoriaItemService.getAll());
    }

    @PostMapping
    public ResponseEntity<CategoriaItemReadDTO> create(@Valid @RequestBody CategoriaItemCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaItemService.create(dto));
    }

    @PutMapping("/{idCategoriaItem}")
    public ResponseEntity<CategoriaItemReadDTO> update(@PathVariable UUID idCategoriaItem,
                                                       @Valid @RequestBody CategoriaItemUpdateDTO dto) {
        return ResponseEntity.ok(categoriaItemService.update(idCategoriaItem, dto));
    }

    @DeleteMapping("/{idCategoriaItem}")
    public ResponseEntity<Void> delete(@PathVariable UUID idCategoriaItem) {
        categoriaItemService.delete(idCategoriaItem);
        return ResponseEntity.noContent().build();
    }
}
