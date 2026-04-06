package com.patopedidosya.local_service.config;

import com.patopedidosya.local_service.model.RubroLocal;
import com.patopedidosya.local_service.repository.IRubroLocalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig {

    @Bean
    public CommandLineRunner rubroLocalSeed(IRubroLocalRepository rubroLocalRepository) {
        return args -> {
            crearRubroSiNoExiste(rubroLocalRepository, "RESTAURANTE", "Locales de comida preparada");
            crearRubroSiNoExiste(rubroLocalRepository, "HELADERIA", "Locales especializados en helados");
            crearRubroSiNoExiste(rubroLocalRepository, "FARMACIA", "Locales de articulos farmaceuticos");
            crearRubroSiNoExiste(rubroLocalRepository, "KIOSCO", "Locales de conveniencia y snacks");
        };
    }

    private void crearRubroSiNoExiste(IRubroLocalRepository repository, String nombre, String descripcion) {
        if (repository.findByNombre(nombre).isEmpty()) {
            RubroLocal rubroLocal = new RubroLocal();
            rubroLocal.setNombre(nombre);
            rubroLocal.setDescripcion(descripcion);
            rubroLocal.setActivo(Boolean.TRUE);
            repository.save(rubroLocal);
        }
    }
}
