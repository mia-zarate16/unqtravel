package unqtravel.catalogo;
import java.util.Objects;

public class AtributoDinamico {
	
	private String nombre;
	private boolean esObligatorio;
	private TipoValorDinamico tipoValor;
	private Object valor;
	
	
	//										CONSTRUCTOR
	public AtributoDinamico(String nombre, boolean esObligatorio, TipoValorDinamico tipoValor, String valor) {
		super();
		this.nombre = Objects.requireNonNull(nombre);
		this.esObligatorio = esObligatorio;
		this.tipoValor = Objects.requireNonNull(tipoValor);
		this.valor = Objects.requireNonNull(this.tipoValor.convertir(Objects.requireNonNull(valor, "El valor no puede ser nulo.")),
	                "La conversión no puede devolver null.");
	}

	
	//										GETTERS
	public String getNombre() {
		return this.nombre;
	}

	public boolean isObligatorio() {
		return this.esObligatorio;
	}


	public TipoValorDinamico getTipoValor() {
		return this.tipoValor;
	}


	public Object getValor() {
		return this.valor;
	}
	
	
	//										OTROS
	public void asignarValor(String nuevoValor) {
		this.valor = Objects.requireNonNull(tipoValor.convertir(nuevoValor), "El valor no puede ser nulo.");
	}
	
}
