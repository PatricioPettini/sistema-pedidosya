package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPromocionRepository extends JpaRepository<Promocion, UUID> {
    List<Promocion> findByIdLocal(UUID idLocal);
    List<Promocion> findByIdItem(UUID idItem);
}

