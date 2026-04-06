package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByIdLocal(UUID idLocal);
    List<Item> findByIdCategoriaItem(UUID idCategoriaItem);
}

