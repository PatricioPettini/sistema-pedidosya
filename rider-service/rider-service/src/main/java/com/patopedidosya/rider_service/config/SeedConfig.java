package com.patopedidosya.rider_service.config;

import com.patopedidosya.rider_service.model.EstadoAsignacionRider;
import com.patopedidosya.rider_service.model.TipoVehiculo;
import com.patopedidosya.rider_service.repository.IEstadoAsignacionRiderRepository;
import com.patopedidosya.rider_service.repository.ITipoVehiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig {

    @Bean
    public CommandLineRunner riderSeeds(ITipoVehiculoRepository tipoVehiculoRepository,
                                        IEstadoAsignacionRiderRepository estadoAsignacionRiderRepository) {
        return args -> {
            crearTipoVehiculoSiNoExiste(tipoVehiculoRepository, "BICICLETA");
            crearTipoVehiculoSiNoExiste(tipoVehiculoRepository, "MOTO");
            crearTipoVehiculoSiNoExiste(tipoVehiculoRepository, "AUTO");

            crearEstadoSiNoExiste(estadoAsignacionRiderRepository, "PENDIENTE", "Asignacion pendiente", 1);
            crearEstadoSiNoExiste(estadoAsignacionRiderRepository, "ACEPTADA", "Asignacion aceptada por el rider", 2);
            crearEstadoSiNoExiste(estadoAsignacionRiderRepository, "RECHAZADA", "Asignacion rechazada por el rider", 3);
            crearEstadoSiNoExiste(estadoAsignacionRiderRepository, "REASIGNADA", "Asignacion transferida a otro rider", 4);
            crearEstadoSiNoExiste(estadoAsignacionRiderRepository, "ENTREGADA", "Entrega completada", 5);
        };
    }

    private void crearTipoVehiculoSiNoExiste(ITipoVehiculoRepository repository, String nombre) {
        if (repository.findByNombre(nombre).isEmpty()) {
            TipoVehiculo tipoVehiculo = new TipoVehiculo();
            tipoVehiculo.setNombre(nombre);
            tipoVehiculo.setActivo(Boolean.TRUE);
            repository.save(tipoVehiculo);
        }
    }

    private void crearEstadoSiNoExiste(IEstadoAsignacionRiderRepository repository,
                                       String nombre,
                                       String descripcion,
                                       int orden) {
        if (repository.findByNombre(nombre).isEmpty()) {
            EstadoAsignacionRider estado = new EstadoAsignacionRider();
            estado.setNombre(nombre);
            estado.setDescripcion(descripcion);
            estado.setOrden(orden);
            repository.save(estado);
        }
    }
}
