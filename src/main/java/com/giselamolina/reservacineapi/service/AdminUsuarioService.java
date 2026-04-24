package com.giselamolina.reservacineapi.service;

import com.giselamolina.reservacineapi.dto.CrearAdminRequest;
import com.giselamolina.reservacineapi.dto.PerfilResponse;
import com.giselamolina.reservacineapi.dto.UsuarioResponse;

import java.util.List;

public interface AdminUsuarioService {

    List<UsuarioResponse> obtenerTodos();

    UsuarioResponse obtenerPorUsername(String username);

    UsuarioResponse crearAdmin(CrearAdminRequest request);

    UsuarioResponse activarUsuario(String username);

    UsuarioResponse desactivarUsuario(String username);

    List<PerfilResponse> obtenerPerfiles();
}