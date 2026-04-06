package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.CategoriaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICategoriaItemRepository extends JpaRepository<CategoriaItem, UUID> {
    List<CategoriaItem> findByIdLocal(UUID idLocal);
}

