package com.giselamolina.reservacineapi.service.impl;

import com.giselamolina.reservacineapi.dto.ReservaRequest;
import com.giselamolina.reservacineapi.dto.ReservaResponse;
import com.giselamolina.reservacineapi.entity.EstadoEvento;
import com.giselamolina.reservacineapi.entity.Evento;
import com.giselamolina.reservacineapi.entity.Reserva;
import com.giselamolina.reservacineapi.entity.Usuario;
import com.giselamolina.reservacineapi.exception.BadRequestException;
import com.giselamolina.reservacineapi.exception.ResourceNotFoundException;
import com.giselamolina.reservacineapi.repository.EventoRepository;
import com.giselamolina.reservacineapi.repository.ReservaRepository;
import com.giselamolina.reservacineapi.repository.UsuarioRepository;
import com.giselamolina.reservacineapi.service.ReservaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              EventoRepository eventoRepository,
                              UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ReservaResponse crearReserva(ReservaRequest request, String username) {

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (evento.getEstado() == EstadoEvento.CANCELADO) {
            throw new BadRequestException("No se puede reservar un evento cancelado");
        }

        if (evento.getEstado() == EstadoEvento.TERMINADO) {
            throw new BadRequestException("No se puede reservar un evento terminado");
        }

        if (request.getCantidadEntradas() <= 0) {
            throw new BadRequestException("La cantidad de entradas debe ser mayor que 0");
        }

        if (request.getCantidadEntradas() > 10) {
            throw new BadRequestException("Máximo 10 entradas por reserva");
        }

        int entradasActuales = reservaRepository.findByEventoId(evento.getId())
                .stream()
                .filter(r -> !r.isCancelada())
                .mapToInt(Reserva::getCantidadEntradas)
                .sum();

        int total = entradasActuales + request.getCantidadEntradas();

        if (total > evento.getAforoMaximo()) {
            throw new BadRequestException("Aforo máximo superado");
        }

        double precioTotal = evento.getPrecio()
                .multiply(new java.math.BigDecimal(request.getCantidadEntradas()))
                .doubleValue();

        Reserva reserva = new Reserva();
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setCantidadEntradas(request.getCantidadEntradas());
        reserva.setPrecioTotal(precioTotal);
        reserva.setFechaReserva(LocalDate.now());
        reserva.setCancelada(false);

        reservaRepository.save(reserva);

        return mapToResponse(reserva);
    }

    @Override
    public List<ReservaResponse> misReservas(String username) {
        return reservaRepository.findByUsuarioUsername(username)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void cancelarReserva(Long id, String username) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        if (!reserva.getUsuario().getUsername().equals(username)) {
            throw new BadRequestException("No puedes cancelar esta reserva");
        }

        if (reserva.isCancelada()) {
            throw new BadRequestException("La reserva ya está cancelada");
        }

        reserva.setCancelada(true);
        reservaRepository.save(reserva);
    }

    private ReservaResponse mapToResponse(Reserva r) {
        ReservaResponse res = new ReservaResponse();
        res.setId(r.getId());
        res.setEventoNombre(r.getEvento().getNombre());
        res.setCantidadEntradas(r.getCantidadEntradas());
        res.setPrecioTotal(r.getPrecioTotal());
        res.setFechaReserva(r.getFechaReserva());
        res.setCancelada(r.isCancelada());
        return res;
    }
}