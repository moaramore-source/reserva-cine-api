package com.giselamolina.reservacineapi.service.impl;

import com.giselamolina.reservacineapi.dto.TipoEventoRequest;
import com.giselamolina.reservacineapi.dto.TipoEventoResponse;
import com.giselamolina.reservacineapi.entity.TipoEvento;
import com.giselamolina.reservacineapi.exception.BadRequestException;
import com.giselamolina.reservacineapi.exception.ConflictException;
import com.giselamolina.reservacineapi.exception.ResourceNotFoundException;
import com.giselamolina.reservacineapi.repository.TipoEventoRepository;
import com.giselamolina.reservacineapi.service.TipoEventoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEventoServiceImpl implements TipoEventoService {

    private final TipoEventoRepository tipoEventoRepository;

    public TipoEventoServiceImpl(TipoEventoRepository tipoEventoRepository) {
        this.tipoEventoRepository = tipoEventoRepository;
    }

    @Override
    public TipoEventoResponse crearTipo(TipoEventoRequest request) {
        if (tipoEventoRepository.existsByNombre(request.getNombre())) {
            throw new ConflictException("Ya existe un tipo de evento con ese nombre");
        }

        TipoEvento tipo = new TipoEvento();
        tipo.setNombre(request.getNombre());
        tipo.setDescripcion(request.getDescripcion());

        tipoEventoRepository.save(tipo);

        return mapToResponse(tipo);
    }

    @Override
    public TipoEventoResponse actualizarTipo(Long id, TipoEventoRequest request) {
        TipoEvento tipo = tipoEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de evento no encontrado"));

        tipoEventoRepository.findByNombre(request.getNombre())
                .ifPresent(existente -> {
                    if (!existente.getId().equals(id)) {
                        throw new ConflictException("Ya existe otro tipo de evento con ese nombre");
                    }
                });

        tipo.setNombre(request.getNombre());
        tipo.setDescripcion(request.getDescripcion());

        tipoEventoRepository.save(tipo);

        return mapToResponse(tipo);
    }

    @Override
    public void eliminarTipo(Long id) {
        TipoEvento tipo = tipoEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de evento no encontrado"));

        if (tipo.getEventos() != null && !tipo.getEventos().isEmpty()) {
            throw new BadRequestException("No se puede eliminar un tipo que ya tiene eventos asociados");
        }

        tipoEventoRepository.delete(tipo);
    }

    @Override
    public TipoEventoResponse obtenerPorId(Long id) {
        TipoEvento tipo = tipoEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de evento no encontrado"));

        return mapToResponse(tipo);
    }

    @Override
    public List<TipoEventoResponse> obtenerTodos() {
        return tipoEventoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TipoEventoResponse mapToResponse(TipoEvento tipo) {
        return new TipoEventoResponse(
                tipo.getId(),
                tipo.getNombre(),
                tipo.getDescripcion()
        );
    }
}