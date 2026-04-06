package com.patopedidosya.user_service.service;

import com.patopedidosya.user_service.dto.AuthResponseDTO;
import com.patopedidosya.user_service.dto.LoginRequestDTO;
import com.patopedidosya.user_service.dto.UsuarioCreateDTO;
import com.patopedidosya.user_service.model.Usuario;
import com.patopedidosya.user_service.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioService usuarioService,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(UsuarioCreateDTO dto) {
        var usuario = usuarioService.create(dto);
        Usuario entity = usuarioService.getByUsername(usuario.getUsername());
        String token = jwtService.generateToken(entity);
        return new AuthResponseDTO(token, usuario);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        Usuario usuario = usuarioService.getByUsername(dto.getUsername());
        String token = jwtService.generateToken(usuario);
        return new AuthResponseDTO(token, usuarioService.toReadDto(usuario));
    }
}
