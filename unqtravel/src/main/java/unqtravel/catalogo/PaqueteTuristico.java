package unqtravel.catalogo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaqueteTuristico implements EntidadCatalogo{
	
	private double descuento; 
	private String nombre;
	private String descripcion;
	private List<EntidadCatalogo> items; 
	
	
	//									CONSTRUCTOR
	
	public PaqueteTuristico(double descuento, String nombre, String descripcion) {
		super();
		this.descuento = descuento;
		this.nombre = Objects.requireNonNull(nombre);
		this.descripcion = Objects.requireNonNull(descripcion);
		this.items = new ArrayList<>();
	}
	
	
	//									GETTERS
	
	public double getDescuento() {
		return descuento;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public List<EntidadCatalogo> getItems() {
	    return new ArrayList<>(items);
	}
	
	
	//									PRECIOS
	
	public double precioBruto() {
		return items.stream()
			   .mapToDouble(EntidadCatalogo::precioFinal)
			   .sum();
	}
	
	public double precioFinal() {
		return precioBruto() * (1.0 - descuento);
	}
	
	
	//									VALIDACIÓN DE LOS ITEMS
	
	public boolean esValido() {
		return items.stream().allMatch(EntidadCatalogo :: esValido);
	}
	
	//									METODOS DE MANIPULACIÓN DE LOS ITEMS
	
	public void agregarItem(EntidadCatalogo item) {
	    Objects.requireNonNull(item);
	    if (item == this) {
	        throw new IllegalArgumentException(
	            "Un paquete no puede contenerse a si mismo"
	        );
	    }
	    items.add(item);
	}
	
	public boolean eliminarItem(EntidadCatalogo item) {
	    return items.remove(item);
	}
	
	public boolean contieneItem(EntidadCatalogo item) {
	    return items.contains(item);
	}
	
	public int cantidadItems() {
	    return items.size();
	}
	
	public EntidadCatalogo obtenerItem(int indice) {
	    return items.get(indice);
	}
	
	public boolean estaVacio() {
	    return items.isEmpty();
	}
	
	
	//									MÉTODOS DE CUPOS
	public boolean hayDisponibilidad(LocalDate fechaInicio,LocalDate fechaFin,int cantidad) {
		return items.stream().allMatch(item -> item.hayDisponibilidad(fechaInicio, fechaFin, cantidad));
	}
	
	public boolean ocuparCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad) {
		if (!hayDisponibilidad(fechaInicio, fechaFin, cantidad)) {
			return false;
		}
		List<EntidadCatalogo> ocupados = new ArrayList<>();
		for (EntidadCatalogo item : items) {
		    if (item.ocuparCupos(fechaInicio, fechaFin, cantidad)) {
		    	ocupados.add(item);
		    } else {
		        for (EntidadCatalogo ocupado : ocupados) {
		           ocupado.liberarCupos(fechaInicio, fechaFin, cantidad);
		        }
		        return false;
		    }
		}
		return true;
	}
	
	public void liberarCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad) {
		    items.forEach(item -> item.liberarCupos(fechaInicio, fechaFin, cantidad));
	}
	
	
}
