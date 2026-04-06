package com.patopedidosya.rider_service.repository;

import com.patopedidosya.rider_service.dto.PedidoReadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order-service")
public interface PedidoAPI {

    @GetMapping("/pedidos/{idPedido}")
    ResponseEntity<PedidoReadDTO> getPedido(@PathVariable UUID idPedido);
}
