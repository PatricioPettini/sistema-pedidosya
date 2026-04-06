package com.patopedidosya.order_service.service;

import com.patopedidosya.order_service.dto.*;
import com.patopedidosya.order_service.model.*;
import com.patopedidosya.order_service.repository.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IPedidoDetalleRepository pedidoDetalleRepository;
    private final IPedidoPromocionRepository pedidoPromocionRepository;
    private final IHistorialEstadoPedidoRepository historialEstadoPedidoRepository;
    private final EstadoPedidoService estadoPedidoService;
    private final UsuarioAPI usuarioAPI;
    private final LocalAPI localAPI;

    public PedidoService(IPedidoRepository pedidoRepository,
                         IPedidoDetalleRepository pedidoDetalleRepository,
                         IPedidoPromocionRepository pedidoPromocionRepository,
                         IHistorialEstadoPedidoRepository historialEstadoPedidoRepository,
                         EstadoPedidoService estadoPedidoService,
                         UsuarioAPI usuarioAPI,
                         LocalAPI localAPI) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoPromocionRepository = pedidoPromocionRepository;
        this.historialEstadoPedidoRepository = historialEstadoPedidoRepository;
        this.estadoPedidoService = estadoPedidoService;
        this.usuarioAPI = usuarioAPI;
        this.localAPI = localAPI;
    }

    public PedidoReadDTO getById(UUID idPedido) {
        return toReadDto(getEntity(idPedido));
    }

    public List<PedidoReadDTO> getAll() {
        List<PedidoReadDTO> response = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.findAll()) {
            response.add(toReadDto(pedido));
        }
        return response;
    }

    public List<PedidoReadDTO> getByUsuario(UUID idUsuario) {
        validarUsuario(idUsuario);
        List<PedidoReadDTO> response = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.findByIdUsuario(idUsuario)) {
            response.add(toReadDto(pedido));
        }
        return response;
    }

    public List<PedidoReadDTO> getByLocal(UUID idLocal) {
        validarLocal(idLocal);
        List<PedidoReadDTO> response = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.findByIdLocal(idLocal)) {
            response.add(toReadDto(pedido));
        }
        return response;
    }

    public PedidoReadDTO create(PedidoCreateDTO dto) {
        validarUsuario(dto.getIdUsuario());
        validarLocal(dto.getIdLocal());
        EstadoPedido estadoInicial = estadoPedidoService.getEntity(dto.getIdEstadoPedido());

        Pedido pedido = new Pedido();
        pedido.setIdUsuario(dto.getIdUsuario());
        pedido.setIdLocal(dto.getIdLocal());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setCiudadEntrega(dto.getCiudadEntrega());
        pedido.setReferenciaEntrega(dto.getReferenciaEntrega());
        pedido.setCostoEnvio(dto.getCostoEnvio());
        pedido.setSubtotal(dto.getSubtotal());
        pedido.setDescuento(dto.getDescuento());
        pedido.setTotal(dto.getTotal());
        pedido.setIdEstadoPedido(estadoInicial.getIdEstadoPedido());
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setFechaEntrega(dto.getFechaEntrega());

        Pedido guardado = pedidoRepository.save(pedido);

        for (PedidoDetalleCreateDTO detalleDto : dto.getDetalles()) {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setIdPedido(guardado.getIdPedido());
            detalle.setIdItem(detalleDto.getIdItem());
            detalle.setNombreItem(detalleDto.getNombreItem());
            detalle.setCantidad(detalleDto.getCantidad());
            detalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
            detalle.setSubtotal(detalleDto.getSubtotal());
            detalle.setObservaciones(detalleDto.getObservaciones());
            pedidoDetalleRepository.save(detalle);
        }

        if (dto.getPromociones() != null) {
            for (PedidoPromocionCreateDTO promocionDto : dto.getPromociones()) {
                PedidoPromocion pedidoPromocion = new PedidoPromocion();
                pedidoPromocion.setIdPedido(guardado.getIdPedido());
                pedidoPromocion.setIdPromocion(promocionDto.getIdPromocion());
                pedidoPromocion.setMontoAplicado(promocionDto.getMontoAplicado());
                pedidoPromocionRepository.save(pedidoPromocion);
            }
        }

        guardarHistorial(guardado.getIdPedido(), null, estadoInicial.getIdEstadoPedido(), "Creacion del pedido");
        return toReadDto(guardado);
    }

    public PedidoReadDTO cambiarEstado(UUID idPedido, PedidoCambioEstadoDTO dto) {
        Pedido pedido = getEntity(idPedido);
        UUID estadoAnterior = pedido.getIdEstadoPedido();
        EstadoPedido nuevoEstado = estadoPedidoService.getEntity(dto.getIdEstadoPedido());
        pedido.setIdEstadoPedido(nuevoEstado.getIdEstadoPedido());
        Pedido actualizado = pedidoRepository.save(pedido);

        guardarHistorial(idPedido, estadoAnterior, nuevoEstado.getIdEstadoPedido(), dto.getDetalle());
        return toReadDto(actualizado);
    }

    public List<HistorialEstadoPedidoReadDTO> getHistorial(UUID idPedido) {
        getEntity(idPedido);
        List<HistorialEstadoPedidoReadDTO> response = new ArrayList<>();
        for (HistorialEstadoPedido historial : historialEstadoPedidoRepository.findByIdPedido(idPedido)) {
            HistorialEstadoPedidoReadDTO dto = new HistorialEstadoPedidoReadDTO();
            dto.setIdHistorialEstadoPedido(historial.getIdHistorialEstadoPedido());
            dto.setIdPedido(historial.getIdPedido());
            dto.setIdEstadoAnterior(historial.getIdEstadoAnterior());
            dto.setIdEstadoNuevo(historial.getIdEstadoNuevo());
            dto.setFecha(historial.getFecha());
            dto.setDetalle(historial.getDetalle());
            response.add(dto);
        }
        return response;
    }

    private void guardarHistorial(UUID idPedido, UUID idEstadoAnterior, UUID idEstadoNuevo, String detalle) {
        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setIdPedido(idPedido);
        historial.setIdEstadoAnterior(idEstadoAnterior);
        historial.setIdEstadoNuevo(idEstadoNuevo);
        historial.setFecha(LocalDateTime.now());
        historial.setDetalle(detalle);
        historialEstadoPedidoRepository.save(historial);
    }

    private Pedido getEntity(UUID idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con id: " + idPedido
                ));
    }

    private void validarUsuario(UUID idUsuario) {
        try {
            ResponseEntity<UsuarioReadDTO> response = usuarioAPI.getUsuario(idUsuario);
            if (response == null || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado con id: " + idUsuario);
            }
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado con id: " + idUsuario);
        }
    }

    private void validarLocal(UUID idLocal) {
        try {
            ResponseEntity<LocalReadDTO> response = localAPI.getLocal(idLocal);
            if (response == null || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
            }
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local no encontrado con id: " + idLocal);
        }
    }

    private PedidoReadDTO toReadDto(Pedido pedido) {
        PedidoReadDTO dto = new PedidoReadDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setIdUsuario(pedido.getIdUsuario());
        dto.setIdLocal(pedido.getIdLocal());
        dto.setDireccionEntrega(pedido.getDireccionEntrega());
        dto.setCiudadEntrega(pedido.getCiudadEntrega());
        dto.setReferenciaEntrega(pedido.getReferenciaEntrega());
        dto.setCostoEnvio(pedido.getCostoEnvio());
        dto.setSubtotal(pedido.getSubtotal());
        dto.setDescuento(pedido.getDescuento());
        dto.setTotal(pedido.getTotal());
        dto.setEstadoPedido(estadoPedidoService.toReadDto(estadoPedidoService.getEntity(pedido.getIdEstadoPedido())));
        dto.setFechaCreacion(pedido.getFechaCreacion());
        dto.setFechaEntrega(pedido.getFechaEntrega());

        List<PedidoDetalleReadDTO> detalles = new ArrayList<>();
        for (PedidoDetalle detalle : pedidoDetalleRepository.findByIdPedido(pedido.getIdPedido())) {
            PedidoDetalleReadDTO detalleDto = new PedidoDetalleReadDTO();
            detalleDto.setIdPedidoDetalle(detalle.getIdPedidoDetalle());
            detalleDto.setIdPedido(detalle.getIdPedido());
            detalleDto.setIdItem(detalle.getIdItem());
            detalleDto.setNombreItem(detalle.getNombreItem());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDto.setSubtotal(detalle.getSubtotal());
            detalleDto.setObservaciones(detalle.getObservaciones());
            detalles.add(detalleDto);
        }
        dto.setDetalles(detalles);

        List<PedidoPromocionReadDTO> promociones = new ArrayList<>();
        for (PedidoPromocion promocion : pedidoPromocionRepository.findByIdPedido(pedido.getIdPedido())) {
            PedidoPromocionReadDTO promocionDto = new PedidoPromocionReadDTO();
            promocionDto.setIdPedidoPromocion(promocion.getIdPedidoPromocion());
            promocionDto.setIdPedido(promocion.getIdPedido());
            promocionDto.setIdPromocion(promocion.getIdPromocion());
            promocionDto.setMontoAplicado(promocion.getMontoAplicado());
            promociones.add(promocionDto);
        }
        dto.setPromociones(promociones);

        return dto;
    }
}

