package unqtravel.catalogo;
import java.util.Objects;

public class AtributoDinamico {
	
	private String nombre;
	private boolean esObligatorio;
	private TipoValorDinamico tipoValor;
	private ValorDinamico valor;
	
	
	//										CONSTRUCTOR
	public AtributoDinamico(String nombre, boolean esObligatorio, TipoValorDinamico tipoValor) {
		super();
		this.nombre = Objects.requireNonNull(nombre);
		this.esObligatorio = esObligatorio;
		this.tipoValor = Objects.requireNonNull(tipoValor);
		this.valor = null;
	}

	
	//										GETTERS
	public String getNombre() {
		return this.nombre;
	}

	public boolean isEsObligatorio() {
		return this.esObligatorio;
	}


	public TipoValorDinamico getTipoValor() {
		return this.tipoValor;
	}


	public ValorDinamico getValor() {
		return this.valor;
	}
	
	
	//										OTROS
	public void asignarValor(ValorDinamico valor) {
		this.valor = valor; 
	}
	
	public boolean esValido() {
		if (this.valor == null) {
			// Ya que si es obligatorio pero no tiene valor asignado entonces es falso.
			return !this.esObligatorio;
		}
		
		return this.valor.esValidoPara(this.tipoValor);
	}
	
}
