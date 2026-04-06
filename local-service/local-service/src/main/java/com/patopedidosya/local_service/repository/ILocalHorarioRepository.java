package com.patopedidosya.local_service.repository;

import com.patopedidosya.local_service.model.LocalHorario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface ILocalHorarioRepository extends JpaRepository<LocalHorario, UUID> {
    List<LocalHorario> findByIdLocal(UUID idLocal);
    List<LocalHorario> findByIdLocalAndDiaSemana(UUID idLocal, DayOfWeek diaSemana);
}

