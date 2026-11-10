package unqtravel.reservaYSusEstados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import unqtravel.catalogo.EntidadCatalogo;

public class Reserva {
	
	private String id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private int cantidad;
	private EstadoReserva estadoActual;
	private Direccion direccionDestino;
	private List<EntidadCatalogo> items;
	
	
	//									CONSTRUCTOR
	public Reserva(String id, LocalDate fechaInicio, LocalDate fechaFin, int cantidad, Direccion direccionDestino) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cantidad = cantidad;
		this.direccionDestino = Objects.requireNonNull(direccionDestino);
		this.estadoActual = new SolicitudPendiente();
		this.items = new ArrayList<>();
	}
	
	
	
	//										GETTERS
	public String getId() {
		return this.id;
	}
	
	public LocalDate getFechaInicio() {
		return this.fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return this.fechaFin;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public Direccion getdireccionDestino() {
		return this.direccionDestino;
	}
	
	public EstadoReserva getEstadoActual() {
		return this.estadoActual;
	}

	public List<EntidadCatalogo> getItems() {
	    return new ArrayList<>(items);
	}
	
	//										OTROS
	public float precioTotal() {
		return this.items.stream().sum(item -> item.precioFinal());
	}
	
	
	//										MÉTODOS DE ESTADO
	public void cambiarEstado(EstadoReserva estadoNuevo) {
		this.estadoActual = estadoNuevo;
	}
	
	public void agregarServicio(EntidadCatalogo item) {
		if(item == null) {
			throw new IllegalArgumentException ("El servicio no puede nulo.");
		}
		if(this.items.contains(item)) {
			throw new IllegalArgumentException ("El servicio ya fue agregado.");
		}
		this.estadoActual.agregarServicio(this, item);
	}
	
	public void quitarServicio(EntidadCatalogo item) {
		if(item == null) {
			throw new IllegalArgumentException ("El servicio no puede nulo.");
		}
		if(!this.items.contains(item)) {
			throw new IllegalArgumentException ("El servicio no fue agregado.");
		}
		this.estadoActual.quitarServicio(this, item);
	}
	
	public void confirmarViaje() {
		this.estadoActual.confirmarViaje(this);
	}
	
	public void realizarCheckin() {
		this.estadoActual.realizarCheckin(this);
	}
	
	public void iniciarViaje() {
		this.estadoActual.iniciarViaje(this);
	}
	
	public void finalizarViaje() {
		this.estadoActual.iniciarViaje(this);
	}
	
	public void cancelarViaje() {
		this.estadoActual.iniciarViaje(this);
	}
	
}



