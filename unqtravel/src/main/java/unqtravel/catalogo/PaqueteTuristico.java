package unqtravel.catalogo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
	
	@Override
    public Set<CategoriaServicio> getCategoria() {
        Set<CategoriaServicio> categorias = new HashSet<>();

        for (EntidadCatalogo componente : items) {
            categorias.addAll(componente.getCategoria());
        }

        return categorias;
    }
	
	//									PRECIOS
	@Override
	public double getPrecioBase() {
		return items.stream()
			   .mapToDouble(EntidadCatalogo::precioFinal)
			   .sum();
	}
	
	
	public double precioFinal() {
		return getPrecioBase() * (1.0 - descuento);
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
	
}
