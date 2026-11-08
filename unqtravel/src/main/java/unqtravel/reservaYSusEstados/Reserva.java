package unqtravel.reservaYSusEstados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.EntidadCatalogo;

public class Reserva {
	
	private String id;
	private double precioFinal;
	private LocalDate fechaReserva;
	private LocalDate fechaPrestacion;
	private EstadoReserva estadoActual;
	private List<EntidadCatalogo> items;
	
	
	//									CONSTRUCTOR
	public Reserva() {
		fechaDeReserva = LocalDate.now();
		estadoActual = new SolicitudPendiente();
		items = new ArrayList<>();
	}
	
	
	
	//									GETTERS
	
	public EstadoReserva getEstadoActual() {
		return this.estadoActual;
	}
	
	public List<EntidadCatalogo> getItems() {
	    return new ArrayList<>(items);
	}
	
	public LocalDate getFechaReserva() { return this.fechaReserva; }
	
	public String getId() { return this.id; }
	
	
	//									MÉTODOS DE ESTADO
	
	public void cambiarEstado(EstadoReserva estadoNuevo) {
		this.estadoActual = estadoNuevo;
	}
}
