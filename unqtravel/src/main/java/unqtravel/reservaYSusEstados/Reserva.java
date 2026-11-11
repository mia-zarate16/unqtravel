package unqtravel.reservaYSusEstados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.comprobantes.Comprobante;
import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.metodoDeTrasladoYTasas.MetodoTraslado;

public class Reserva {
	
	private String id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private int cantidad;
	private EstadoReserva estadoActual;
	private Direccion direccionDestino;
	private List<EntidadCatalogo> items;
	private MetodoTraslado metodoDeTraslado;
	private int cupoDeGuias;
	private RegistroComprobante registroDeComprobantes;
	
	
	//									CONSTRUCTOR
	public Reserva(String id, LocalDate fechaInicio, LocalDate fechaFin, int cantidad, Direccion direccionDestino,
			MetodoTraslado metodoDeTraslado, int cupoDeGuias, RegistroComprobante registroDeComprobantes) {
		super();
		if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador no puede ser nulo ni vacío.");
        }
		if (fechaInicio == null || fechaFin == null) {
	        throw new IllegalArgumentException("Las fechas no pueden ser nulas");
	    }
		if (fechaInicio.isAfter(fechaFin)) {
	        throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
	    }
		if (cantidad <= 0) {
	        throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
	    }
		if (direccionDestino == null) {
            throw new IllegalArgumentException("La dirección de destino no puede ser nula.");
        }
		if(metodoDeTraslado == null) {
			throw new IllegalArgumentException("El método de traslado no puede ser nulo.");
		}
		if (cupoDeGuias < 0) {
	        throw new IllegalArgumentException("Los cupos de guias no pueden ser negativos.");
	    }
		if (registroDeComprobantes == null) {
	        throw new IllegalArgumentException("El registro de comprobantes no puede ser nulo.");
	    }
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cantidad = cantidad;
		this.direccionDestino = direccionDestino;
		this.metodoDeTraslado = metodoDeTraslado;
		this.cupoDeGuias = cupoDeGuias;
		this.registroDeComprobantes = registroDeComprobantes;
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
	
	public Direccion getDireccionDestino() {
		return this.direccionDestino;
	}
	
	public MetodoTraslado getMetodoDeTraslado() {
		return this.metodoDeTraslado;
	}
	
	public int getCupoDeGuias() {
		return this.cupoDeGuias;
	}
	
	public EstadoReserva getEstadoActual() {
		return this.estadoActual;
	}
	
	public RegistroComprobante getRegistroDeComprobantes() {
		return this.registroDeComprobantes;
	}

	public List<EntidadCatalogo> getItems() {
	    return new ArrayList<>(items);
	}
	
	
	//										OTROS
	public float precioTotal() {
		return (float) this.items.stream().mapToDouble(item -> item.precioFinal()).sum();
	}
	
	void agregarItem(EntidadCatalogo item) {
		if (item == null) {
	        throw new IllegalArgumentException("El servicio no puede ser nulo.");
	    }
	    if (this.items.contains(item)) {
	        throw new IllegalArgumentException("El servicio ya fue agregado.");
	    }
		this.items.add(item);
	}

	void quitarItem(EntidadCatalogo item) {
		if (item == null) {
	        throw new IllegalArgumentException("El servicio no puede ser nulo.");
	    }
	    if (!this.items.contains(item)) {
	        throw new IllegalArgumentException("El servicio no fue agregado.");
	    }
	    this.items.remove(item);
	}
	
	public boolean hayDisponibilidadEnPuntoDeEncuentro() {
		return this.cupoDeGuias > 0;
	}
	
	void registrarComprobante(Comprobante voucher) {
		this.registroDeComprobantes.registrarComprobante(voucher);
	}
	
	public float costoDelTraslado() {
	    return metodoDeTraslado.calcularCosto(this);
	}
	
	public float importeReembolsableEnCheckIn() {
	    return precioTotal() + costoDelTraslado();
	}
	
	//										MÉTODOS DE ESTADO
	void cambiarEstado(EstadoReserva estadoNuevo) {
		if (estadoNuevo == null) {
            throw new IllegalArgumentException("El estado nuevo no puede ser nulo.");
        }
		this.estadoActual = estadoNuevo;
	}
	
	public void agregarServicio(EntidadCatalogo item) {
		this.estadoActual.agregarServicio(this, item);
	}
	
	public void quitarServicio(EntidadCatalogo item) {
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
		this.estadoActual.finalizarViaje(this);
	}
	
	public void cancelarViaje() {
		this.estadoActual.cancelarViaje(this);
	}
	
	
	//											MÉTODOS DE CUPO
	boolean ocuparCupos() {
		if (this.items.isEmpty()) {
	        return false;
	    }
		if(!hayDisponibilidadParaLaReserva()) {
	    	return false;
	    }
	    List<EntidadCatalogo> ocupados = new ArrayList<>();
	    for (EntidadCatalogo item : items) {
	        if (item.ocuparCupos(fechaInicio, fechaFin, cantidad)) {
	            ocupados.add(item);
	        } else {
	        	liberarLosCupos(ocupados);
	            return false;
	        }
	    }
	    return true;
	}
	
	boolean hayDisponibilidadParaLaReserva() {
		return this.items.stream().allMatch(item -> item.hayDisponibilidad(fechaInicio, fechaFin, cantidad));
	}
	
	void liberarLosCupos(List<EntidadCatalogo> ocupados) {
		ocupados.stream().forEach(ocupado -> ocupado.liberarCupos(fechaInicio, fechaFin, cantidad));
	}
	

	void liberarCupos() {
		this.items.stream().forEach(item -> item.liberarCupos(fechaInicio, fechaFin, cantidad));
	}

}



