package com.patopedidosya.cart_service.service;

import com.patopedidosya.cart_service.dto.*;
import com.patopedidosya.cart_service.model.Carrito;
import com.patopedidosya.cart_service.model.CarritoDetalle;
import com.patopedidosya.cart_service.model.CarritoPromocion;
import com.patopedidosya.cart_service.repository.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarritoService {

    private final ICarritoRepository carritoRepository;
    private final ICarritoDetalleRepository carritoDetalleRepository;
    private final ICarritoPromocionRepository carritoPromocionRepository;
    private final UsuarioAPI usuarioAPI;
    private final LocalAPI localAPI;
    private final ItemAPI itemAPI;

    public CarritoService(ICarritoRepository carritoRepository,
                          ICarritoDetalleRepository carritoDetalleRepository,
                          ICarritoPromocionRepository carritoPromocionRepository,
                          UsuarioAPI usuarioAPI,
                          LocalAPI localAPI,
                          ItemAPI itemAPI) {
        this.carritoRepository = carritoRepository;
        this.carritoDetalleRepository = carritoDetalleRepository;
        this.carritoPromocionRepository = carritoPromocionRepository;
        this.usuarioAPI = usuarioAPI;
        this.localAPI = localAPI;
        this.itemAPI = itemAPI;
    }

    public CarritoReadDTO getById(UUID idCarrito) {
        return toReadDto(getEntity(idCarrito));
    }

    public List<CarritoReadDTO> getByUsuario(UUID idUsuario) {
        validarUsuario(idUsuario);
        List<CarritoReadDTO> response = new ArrayList<>();
        for (Carrito carrito : carritoRepository.findByIdUsuario(idUsuario)) {
            response.add(toReadDto(carrito));
        }
        return response;
    }

    public CarritoReadDTO create(CarritoCreateDTO dto) {
        validarUsuario(dto.getIdUsuario());
        validarLocal(dto.getIdLocal());

        var existente = carritoRepository.findByIdUsuarioAndIdLocalAndActivo(dto.getIdUsuario(), dto.getIdLocal(), Boolean.TRUE);
        if (existente.isPresent()) {
            return toReadDto(existente.get());
        }

        Carrito carrito = new Carrito();
        carrito.setIdUsuario(dto.getIdUsuario());
        carrito.setIdLocal(dto.getIdLocal());
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setActivo(Boolean.TRUE);
        carrito.setFechaCreacion(LocalDateTime.now());
        carrito.setFechaActualizacion(LocalDateTime.now());
        return toReadDto(carritoRepository.save(carrito));
    }

    public void cerrar(UUID idCarrito) {
        Carrito carrito = getEntity(idCarrito);
        carrito.setActivo(Boolean.FALSE);
        carrito.setFechaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    public CarritoDetalleReadDTO agregarDetalle(UUID idCarrito, CarritoDetalleCreateDTO dto) {
        Carrito carrito = getEntity(idCarrito);
        ItemReadDTO item = validarYObtenerItem(dto.getIdItem(), carrito.getIdLocal());

        CarritoDetalle detalle = carritoDetalleRepository.findByIdCarritoAndIdItem(idCarrito, dto.getIdItem())
                .orElseGet(() -> {
                    CarritoDetalle nuevo = new CarritoDetalle();
                    nuevo.setIdCarrito(idCarrito);
                    nuevo.setIdItem(dto.getIdItem());
                    return nuevo;
                });

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(item.getPrecio());
        detalle.setSubtotal(item.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad())));

        CarritoDetalle guardado = carritoDetalleRepository.save(detalle);
        recalcularCarrito(carrito);
        return toDetalleReadDto(guardado);
    }

    public CarritoDetalleReadDTO actualizarDetalle(UUID idCarritoDetalle, CarritoDetalleUpdateDTO dto) {
        CarritoDetalle detalle = getDetalleEntity(idCarritoDetalle);
        ItemReadDTO item = getItem(detalle.getIdItem());

        if (dto.getCantidad() != null) {
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setSubtotal(item.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad())));
        }

        CarritoDetalle guardado = carritoDetalleRepository.save(detalle);
        recalcularCarrito(getEntity(detalle.getIdCarrito()));
        return toDetalleReadDto(guardado);
    }

    public void eliminarDetalle(UUID idCarritoDetalle) {
        CarritoDetalle detalle = getDetalleEntity(idCarritoDetalle);
        UUID idCarrito = detalle.getIdCarrito();
        carritoDetalleRepository.delete(detalle);
        recalcularCarrito(getEntity(idCarrito));
    }

    public CarritoPromocionReadDTO agregarPromocion(UUID idCarrito, CarritoPromocionCreateDTO dto) {
        Carrito carrito = getEntity(idCarrito);
        CarritoPromocion promocion = new CarritoPromocion();
        promocion.setIdCarrito(idCarrito);
        promocion.setIdPromocion(dto.getIdPromocion());
        promocion.setMontoAplicado(dto.getMontoAplicado());
        CarritoPromocion guardada = carritoPromocionRepository.save(promocion);
        recalcularCarrito(carrito);
        return toPromocionReadDto(guardada);
    }

    public void eliminarPromocion(UUID idCarritoPromocion) {
        CarritoPromocion promocion = carritoPromocionRepository.findById(idCarritoPromocion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion de carrito no encontrada"));
        UUID idCarrito = promocion.getIdCarrito();
        carritoPromocionRepository.delete(promocion);
        recalcularCarrito(getEntity(idCarrito));
    }

    private void recalcularCarrito(Carrito carrito) {
        BigDecimal subtotal = carritoDetalleRepository.findByIdCarrito(carrito.getIdCarrito()).stream()
                .map(CarritoDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal descuento = carritoPromocionRepository.findByIdCarrito(carrito.getIdCarrito()).stream()
                .map(CarritoPromocion::getMontoAplicado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carrito.setSubtotal(subtotal);
        carrito.setDescuento(descuento);
        carrito.setTotal(subtotal.subtract(descuento));
        carrito.setFechaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    private Carrito getEntity(UUID idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado con id: " + idCarrito));
    }

    private CarritoDetalle getDetalleEntity(UUID idCarritoDetalle) {
        return carritoDetalleRepository.findById(idCarritoDetalle)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle de carrito no encontrado con id: " + idCarritoDetalle));
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

    private ItemReadDTO validarYObtenerItem(UUID idItem, UUID idLocal) {
        ItemReadDTO item = getItem(idItem);
        if (!item.getIdLocal().equals(idLocal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El item no pertenece al local del carrito");
        }
        if (!Boolean.TRUE.equals(item.getDisponible())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El item no esta disponible");
        }
        return item;
    }

    private ItemReadDTO getItem(UUID idItem) {
        try {
            ResponseEntity<ItemReadDTO> response = itemAPI.getItem(idItem);
            if (response == null || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item no encontrado con id: " + idItem);
            }
            return response.getBody();
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item no encontrado con id: " + idItem);
        }
    }

    private CarritoReadDTO toReadDto(Carrito carrito) {
        CarritoReadDTO dto = new CarritoReadDTO();
        dto.setIdCarrito(carrito.getIdCarrito());
        dto.setIdUsuario(carrito.getIdUsuario());
        dto.setIdLocal(carrito.getIdLocal());
        dto.setSubtotal(carrito.getSubtotal());
        dto.setDescuento(carrito.getDescuento());
        dto.setTotal(carrito.getTotal());
        dto.setActivo(carrito.getActivo());
        dto.setFechaCreacion(carrito.getFechaCreacion());
        dto.setFechaActualizacion(carrito.getFechaActualizacion());

        List<CarritoDetalleReadDTO> detalles = new ArrayList<>();
        for (CarritoDetalle detalle : carritoDetalleRepository.findByIdCarrito(carrito.getIdCarrito())) {
            detalles.add(toDetalleReadDto(detalle));
        }
        dto.setDetalles(detalles);

        List<CarritoPromocionReadDTO> promociones = new ArrayList<>();
        for (CarritoPromocion promocion : carritoPromocionRepository.findByIdCarrito(carrito.getIdCarrito())) {
            promociones.add(toPromocionReadDto(promocion));
        }
        dto.setPromociones(promociones);

        return dto;
    }

    private CarritoDetalleReadDTO toDetalleReadDto(CarritoDetalle detalle) {
        CarritoDetalleReadDTO dto = new CarritoDetalleReadDTO();
        dto.setIdCarritoDetalle(detalle.getIdCarritoDetalle());
        dto.setIdCarrito(detalle.getIdCarrito());
        dto.setItem(getItem(detalle.getIdItem()));
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }

    private CarritoPromocionReadDTO toPromocionReadDto(CarritoPromocion promocion) {
        CarritoPromocionReadDTO dto = new CarritoPromocionReadDTO();
        dto.setIdCarritoPromocion(promocion.getIdCarritoPromocion());
        dto.setIdCarrito(promocion.getIdCarrito());
        dto.setIdPromocion(promocion.getIdPromocion());
        dto.setMontoAplicado(promocion.getMontoAplicado());
        return dto;
    }
}

