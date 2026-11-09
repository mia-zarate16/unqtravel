package unqtravel.catalogo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ServicioIndividual implements EntidadCatalogo{
	
	private String id;
	private String nombre;
	private Proveedor operador;
	private double precioBase;
	private double descuento;
	private String descripcion;
	private CategoriaServicio categoria;
	private Map<String, AtributoDinamico> atributosDinamicos; 
	
	
	//										CONSTRUCTOR
	
	public ServicioIndividual(String id, String nombre, Proveedor operador, double precioBase, double descuento, 
			String descripcion, CategoriaServicio categoria) {
		this.id = id;
		this.nombre = Objects.requireNonNull(nombre);
		this.operador = Objects.requireNonNull(operador);
		this.precioBase = precioBase;
		this.descuento = descuento;
		this.descripcion = Objects.requireNonNull(descripcion);
		this.categoria = Objects.requireNonNull(categoria);
		this.atributosDinamicos = new HashMap<>();
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
	
	public CategoriaServicio getCategoria() {
		return this.categoria;
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
}
