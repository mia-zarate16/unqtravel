package unqtravel.reservaYSusEstados;

import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.EntidadCatalogo;

public class Reserva {
	
	private EstadoReserva estadoActual;
	private List<EntidadCatalogo> items;
	
	
	//									CONSTRUCTOR
	public Reserva() {
		super();
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
	
	
	//									MÉTODOS DE ESTADO
	
	public void cambiarEstado(EstadoReserva estadoNuevo) {
		this.estadoActual = estadoNuevo;
	}



