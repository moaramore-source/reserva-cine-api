package com.giselamolina.reservacineapi.service.impl;

import com.giselamolina.reservacineapi.dto.EventoRequest;
import com.giselamolina.reservacineapi.dto.EventoResponse;
import com.giselamolina.reservacineapi.entity.EstadoEvento;
import com.giselamolina.reservacineapi.entity.Evento;
import com.giselamolina.reservacineapi.entity.TipoEvento;
import com.giselamolina.reservacineapi.exception.ResourceNotFoundException;
import com.giselamolina.reservacineapi.repository.EventoRepository;
import com.giselamolina.reservacineapi.repository.ReservaRepository;
import com.giselamolina.reservacineapi.repository.TipoEventoRepository;
import com.giselamolina.reservacineapi.service.EventoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final ReservaRepository reservaRepository;

    public EventoServiceImpl(EventoRepository eventoRepository,
                             TipoEventoRepository tipoEventoRepository,
                             ReservaRepository reservaRepository) {
        this.eventoRepository = eventoRepository;
        this.tipoEventoRepository = tipoEventoRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public EventoResponse crearEvento(EventoRequest request) {
        TipoEvento tipoEvento = tipoEventoRepository.findById(request.getTipoEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de evento no encontrado"));

        Evento evento = new Evento();
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setDuracion(request.getDuracion());
        evento.setDireccion(request.getDireccion());
        evento.setEstado(EstadoEvento.ACTIVO);
        evento.setDestacado(request.getDestacado());
        evento.setAforoMaximo(request.getAforoMaximo());
        evento.setMinimoAsistencia(request.getMinimoAsistencia());
        evento.setPrecio(request.getPrecio());
        evento.setTipoEvento(tipoEvento);

        eventoRepository.save(evento);

        return mapToResponse(evento);
    }

    @Override
    public EventoResponse actualizarEvento(Long id, EventoRequest request) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        TipoEvento tipoEvento = tipoEventoRepository.findById(request.getTipoEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de evento no encontrado"));

        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setDuracion(request.getDuracion());
        evento.setDireccion(request.getDireccion());
        evento.setDestacado(request.getDestacado());
        evento.setAforoMaximo(request.getAforoMaximo());
        evento.setMinimoAsistencia(request.getMinimoAsistencia());
        evento.setPrecio(request.getPrecio());
        evento.setTipoEvento(tipoEvento);

        actualizarEstadoSiCorresponde(evento);

        eventoRepository.save(evento);

        return mapToResponse(evento);
    }

    @Override
    public EventoResponse cancelarEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        evento.setEstado(EstadoEvento.CANCELADO);
        eventoRepository.save(evento);

        return mapToResponse(evento);
    }

    @Override
    public EventoResponse obtenerEventoPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        actualizarEstadoSiCorresponde(evento);
        eventoRepository.save(evento);

        return mapToResponse(evento);
    }

    @Override
    public List<EventoResponse> obtenerTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        eventos.forEach(this::actualizarEstadoSiCorresponde);
        eventoRepository.saveAll(eventos);
        return eventos.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<EventoResponse> obtenerActivos() {
        List<Evento> eventos = eventoRepository.findByEstado(EstadoEvento.ACTIVO);
        eventos.forEach(this::actualizarEstadoSiCorresponde);
        eventoRepository.saveAll(eventos);
        return eventos.stream()
                .filter(e -> e.getEstado() == EstadoEvento.ACTIVO)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EventoResponse> obtenerDestacados() {
        List<Evento> eventos = eventoRepository.findByEstadoAndDestacadoTrue(EstadoEvento.ACTIVO);
        eventos.forEach(this::actualizarEstadoSiCorresponde);
        eventoRepository.saveAll(eventos);
        return eventos.stream()
                .filter(e -> e.getEstado() == EstadoEvento.ACTIVO && Boolean.TRUE.equals(e.getDestacado()))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EventoResponse> obtenerCancelados() {
        return eventoRepository.findByEstado(EstadoEvento.CANCELADO)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EventoResponse> obtenerTerminados() {
        List<Evento> eventos = eventoRepository.findAll();
        eventos.forEach(this::actualizarEstadoSiCorresponde);
        eventoRepository.saveAll(eventos);

        return eventos.stream()
                .filter(e -> e.getEstado() == EstadoEvento.TERMINADO)
                .map(this::mapToResponse)
                .toList();
    }

    private void actualizarEstadoSiCorresponde(Evento evento) {
        if (evento.getEstado() != EstadoEvento.CANCELADO
                && evento.getFechaInicio() != null
                && evento.getFechaInicio().isBefore(LocalDate.now())) {
            evento.setEstado(EstadoEvento.TERMINADO);
        }
    }

    private int calcularPlazasDisponibles(Evento evento) {
        int reservadas = reservaRepository.findByEventoId(evento.getId())
                .stream()
                .filter(r -> !r.isCancelada())
                .mapToInt(r -> r.getCantidadEntradas())
                .sum();

        return evento.getAforoMaximo() - reservadas;
    }

    private EventoResponse mapToResponse(Evento evento) {
        return new EventoResponse(
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getFechaInicio(),
                evento.getDuracion(),
                evento.getDireccion(),
                evento.getEstado().name(),
                evento.getDestacado(),
                evento.getAforoMaximo(),
                evento.getMinimoAsistencia(),
                evento.getPrecio(),
                evento.getTipoEvento() != null ? evento.getTipoEvento().getId() : null,
                evento.getTipoEvento() != null ? evento.getTipoEvento().getNombre() : null,
                calcularPlazasDisponibles(evento)
        );
    }
}