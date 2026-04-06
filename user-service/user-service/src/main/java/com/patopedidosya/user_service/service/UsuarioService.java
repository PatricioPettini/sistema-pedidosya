package com.patopedidosya.user_service.service;

import com.patopedidosya.user_service.dto.UsuarioCreateDTO;
import com.patopedidosya.user_service.dto.UsuarioReadDTO;
import com.patopedidosya.user_service.dto.UsuarioUpdateDTO;
import com.patopedidosya.user_service.model.Usuario;
import com.patopedidosya.user_service.repository.IUsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioReadDTO getById(UUID idUsuario) {
        return toReadDto(getEntity(idUsuario));
    }

    public List<UsuarioReadDTO> getAll() {
        List<UsuarioReadDTO> response = new ArrayList<>();
        for (Usuario usuario : usuarioRepository.findAll()) {
            response.add(toReadDto(usuario));
        }
        return response;
    }

    public UsuarioReadDTO create(UsuarioCreateDTO dto) {
        validarUnicidad(dto.getUsername(), dto.getEmail(), dto.getTelefono(), null);

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(Boolean.TRUE);
        usuario.setFechaAlta(LocalDateTime.now());
        return toReadDto(usuarioRepository.save(usuario));
    }

    public UsuarioReadDTO update(UUID idUsuario, UsuarioUpdateDTO dto) {
        Usuario usuario = getEntity(idUsuario);
        validarUnicidad(dto.getUsername(), dto.getEmail(), dto.getTelefono(), usuario);

        if (dto.getUsername() != null) usuario.setUsername(dto.getUsername());
        if (dto.getPassword() != null) usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
        if (dto.getTelefono() != null) usuario.setTelefono(dto.getTelefono());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getFechaNacimiento() != null) usuario.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getActivo() != null) usuario.setActivo(dto.getActivo());

        return toReadDto(usuarioRepository.save(usuario));
    }

    public void delete(UUID idUsuario) {
        usuarioRepository.delete(getEntity(idUsuario));
    }

    public Usuario getEntity(UUID idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + idUsuario
                ));
    }

    public Usuario getByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con username: " + username
                ));
    }

    private void validarUnicidad(String username, String email, String telefono, Usuario actual) {
        if (username != null && usuarioRepository.existsByUsername(username)
                && (actual == null || !actual.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El username ya existe");
        }

        if (email != null && usuarioRepository.existsByEmail(email)
                && (actual == null || !actual.getEmail().equals(email))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya existe");
        }

        if (telefono != null && usuarioRepository.existsByTelefono(telefono)
                && (actual == null || !actual.getTelefono().equals(telefono))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El telefono ya existe");
        }
    }

    public UsuarioReadDTO toReadDto(Usuario usuario) {
        UsuarioReadDTO dto = new UsuarioReadDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setEmail(usuario.getEmail());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setActivo(usuario.getActivo());
        dto.setFechaAlta(usuario.getFechaAlta());
        return dto;
    }
}
