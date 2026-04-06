package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.LocalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILocalRepository extends JpaRepository<LocalEntity, UUID> {
}
