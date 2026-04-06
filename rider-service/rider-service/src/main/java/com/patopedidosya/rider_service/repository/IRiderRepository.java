package com.patopedidosya.rider_service.repository;

import com.patopedidosya.rider_service.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRiderRepository extends JpaRepository<Rider, UUID> {
}
