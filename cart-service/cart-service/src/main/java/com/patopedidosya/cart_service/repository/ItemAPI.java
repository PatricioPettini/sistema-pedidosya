package com.patopedidosya.cart_service.repository;

import com.patopedidosya.cart_service.dto.ItemReadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "local-service", contextId = "cartItemClient")
public interface ItemAPI {
    @GetMapping("/items/{idItem}")
    ResponseEntity<ItemReadDTO> getItem(@PathVariable UUID idItem);
}
