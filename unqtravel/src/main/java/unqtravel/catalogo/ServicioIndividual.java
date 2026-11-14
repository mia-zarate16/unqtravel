package unqtravel.catalogo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class ServicioIndividual implements EntidadCatalogo{
	
	private String id;
	private String nombre;
	private Proveedor operador;
	private double precioBase;
	private double descuento;
	private String descripcion;
	private CategoriaServicio categoria;
	private Map<String, AtributoDinamico> atributosDinamicos; 
	private int capacidadMaxima;
	private Map<LocalDate, Integer> ocupados;
	
	
	//										CONSTRUCTOR
	
	public ServicioIndividual(String id, String nombre, Proveedor operador, double precioBase, double descuento, 
			String descripcion, CategoriaServicio categoria, int capacidadMaxima) {
		super();
		if(operador == null) {
			throw new IllegalArgumentException("El operador no puede ser nulo.");
		}
		this.id = id;
		this.nombre = nombre;
		this.operador = operador;
		this.precioBase = precioBase;
		this.descuento = descuento;
		this.descripcion = Objects.requireNonNull(descripcion);
		this.categoria = Objects.requireNonNull(categoria);
		this.atributosDinamicos = new HashMap<>();
		if (capacidadMaxima <= 0) {
	        throw new IllegalArgumentException("La capacidad máxima debe ser positiva.");
	    }
		this.capacidadMaxima = capacidadMaxima;
		this.ocupados = new HashMap<>();
	}

	
	//										GETTERS
	
	public String getId() {
		return id;
	}
	
	public Proveedor getOperador() {
		return this.operador;
	}
	
	public double getPrecioBase() {
		return this.precioBase;
	}
	
	public double getDescuento() {
		return this.descuento;
	}
	
	public Set<CategoriaServicio> getCategoria() {
		return Set.of(categoria);
	}
	
	public List<AtributoDinamico> getAtributosDinamicos() {
	    return new ArrayList<>(this.atributosDinamicos.values());
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public int getCapacidadMaxima() {
	    return this.capacidadMaxima;
	}
	
	//										OTROS
	
	public double precioFinal() {
		return this.precioBase * (1.0 - this.descuento);
	}
	
	
	
	//										VALIDACIÓN DE ATRIBUTOS
	public boolean esValido() {
		return esValidoNombre() && esValidoID();
	}

	public boolean esValidoNombre() {
		return this.nombre != null && !this.nombre.isBlank();
	}
	
	public boolean esValidoID() {
		return this.id != null && !this.id.isBlank();
	}
	
	
	//										METODOS DE MANIPULACIÓN DE LOS ATRIBUTOS
	
	public void agregarAtributo(AtributoDinamico a) {
		Objects.requireNonNull(a);
		String nombre = a.getNombre();
		if (this.atributosDinamicos.containsKey(nombre)) {
			throw new IllegalArgumentException(
	                "El atributo ya existe"
			);
		}
		this.atributosDinamicos.put(nombre, a);
	}
	
	public void eliminarAtributo(String nombre) {
	    this.atributosDinamicos.remove(nombre);
	}
	
	public AtributoDinamico obtenerAtributo(String nombre) {
		return this.atributosDinamicos.get(nombre);
	}
	
	public boolean tieneAtributo(String nombre) {
	    return this.atributosDinamicos.containsKey(nombre);
	}
	
	public void asignarValorAtributo(AtributoDinamico a, String valor) {
		Objects.requireNonNull(a);
	    if (atributosDinamicos.get(a.getNombre()) != a) {
	        throw new IllegalArgumentException(
	            "El atributo no pertenece al servicio"
	        );
	    }
	    a.asignarValor(valor);
	}
	
	public Integer cuposOcupados(LocalDate fecha) {
	  return ocupados.getOrDefault(fecha,0);
	}
	
	
	//										MÉTODOS DE CUPOS
	public boolean hayDisponibilidad(LocalDate fechaInicio,  LocalDate fechaFin, int cantidad) {
	    validarSolicitud(fechaInicio, fechaFin, cantidad);
	    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
	        int cantidadOcupada = ocupados.getOrDefault(fecha, 0);
	        if (cantidad > capacidadMaxima - cantidadOcupada) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void validarSolicitud(LocalDate fechaInicio, LocalDate fechaFin, int cantidad) {
	    Objects.requireNonNull(fechaInicio);
	    Objects.requireNonNull(fechaFin);
	    if (fechaInicio.isAfter(fechaFin)) {
	        throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
	    }
	    if (cantidad <= 0) {
	        throw new IllegalArgumentException("La cantidad de cupos debe ser positiva.");
	    }
	}
	
	public boolean ocuparCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad) {
	    if (!hayDisponibilidad(fechaInicio, fechaFin, cantidad)) {
	        return false;
	    }
	    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
	        int cantidadOcupada = ocupados.getOrDefault(fecha, 0);
	        ocupados.put(fecha, cantidadOcupada + cantidad);
	    }
	    return true;
	}
	
	public void liberarCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad) {
	    validarSolicitud(fechaInicio, fechaFin, cantidad);
	    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
	        int cantidadOcupada = ocupados.getOrDefault(fecha, 0);
	        if (cantidadOcupada < cantidad) {
	            throw new IllegalArgumentException("No hay suficientes cupos ocupados para liberar.");
	        }
	    }
	    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
	        int cantidadOcupada = ocupados.get(fecha);
	        int nuevaCantidad = cantidadOcupada - cantidad;
	        if (nuevaCantidad == 0) {
	            ocupados.remove(fecha);
	        } else {
	            ocupados.put(fecha, nuevaCantidad);
	        }
	    }
	}


	@Override
	public Set<ServicioIndividual> obtenerServiciosIndividuales() {
	  return Set.of(this);
	}
}
