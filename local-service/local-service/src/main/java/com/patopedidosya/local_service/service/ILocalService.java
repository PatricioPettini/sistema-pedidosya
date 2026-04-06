package com.patopedidosya.local_service.service;

import com.patopedidosya.local_service.dto.LocalCreateDTO;
import com.patopedidosya.local_service.dto.LocalReadDTO;
import com.patopedidosya.local_service.dto.LocalUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ILocalService {
    public LocalReadDTO getLocalDto(UUID idLocal);
    public List<LocalReadDTO> getAll();
    public LocalReadDTO createLocal(LocalCreateDTO dto);
    public LocalReadDTO updateLocal(UUID idLocal, LocalUpdateDTO dto);
    public void deleteLocal(UUID id);
}
