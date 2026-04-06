package com.patopedidosya.order_service.config;

import com.patopedidosya.order_service.model.EstadoPedido;
import com.patopedidosya.order_service.repository.IEstadoPedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig {

    @Bean
    public CommandLineRunner estadoPedidoSeed(IEstadoPedidoRepository estadoPedidoRepository) {
        return args -> {
            crearEstadoSiNoExiste(estadoPedidoRepository, "PENDIENTE", "Pedido creado y pendiente de confirmacion", 1, Boolean.FALSE);
            crearEstadoSiNoExiste(estadoPedidoRepository, "CONFIRMADO", "Pedido confirmado por el local", 2, Boolean.FALSE);
            crearEstadoSiNoExiste(estadoPedidoRepository, "EN_PREPARACION", "Pedido en preparacion", 3, Boolean.FALSE);
            crearEstadoSiNoExiste(estadoPedidoRepository, "EN_CAMINO", "Pedido despachado y en camino", 4, Boolean.FALSE);
            crearEstadoSiNoExiste(estadoPedidoRepository, "ENTREGADO", "Pedido entregado al cliente", 5, Boolean.TRUE);
            crearEstadoSiNoExiste(estadoPedidoRepository, "CANCELADO", "Pedido cancelado", 6, Boolean.TRUE);
        };
    }

    private void crearEstadoSiNoExiste(IEstadoPedidoRepository repository,
                                       String nombre,
                                       String descripcion,
                                       int orden,
                                       Boolean esFinal) {
        if (repository.findByNombre(nombre).isEmpty()) {
            EstadoPedido estadoPedido = new EstadoPedido();
            estadoPedido.setNombre(nombre);
            estadoPedido.setDescripcion(descripcion);
            estadoPedido.setOrden(orden);
            estadoPedido.setEsFinal(esFinal);
            repository.save(estadoPedido);
        }
    }
}
