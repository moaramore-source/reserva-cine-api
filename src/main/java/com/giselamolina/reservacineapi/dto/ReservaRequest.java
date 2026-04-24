package com.giselamolina.reservacineapi.dto;

public class ReservaRequest {

    private Long eventoId;
    private int cantidadEntradas;
    
    // getters y setters
	public Long getEventoId() {
		return eventoId;
	}
	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}
	public int getCantidadEntradas() {
		return cantidadEntradas;
	}
	public void setCantidadEntradas(int cantidadEntradas) {
		this.cantidadEntradas = cantidadEntradas;
	}
}