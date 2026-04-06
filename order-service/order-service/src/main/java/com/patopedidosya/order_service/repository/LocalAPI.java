package com.patopedidosya.order_service.repository;

import com.patopedidosya.order_service.dto.LocalReadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "local-service")
public interface LocalAPI {

    @GetMapping("/locales/{idLocal}")
    ResponseEntity<LocalReadDTO> getLocal(@PathVariable UUID idLocal);
}

