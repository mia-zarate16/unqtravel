package unqtravel.catalogo;

public class ValorBooleano implements ValorDinamico{

	private Boolean valor;

	
	//								CONSTRUCTOR
	public ValorBooleano(Boolean valor) {
		super();
		this.valor = valor;
	}
	
	
	//								MÉTODO PARA VALIDAR
	@Override
	public boolean esValidoPara(TipoValorDinamico tipo) {
		return tipo.esValido(valor);
	}
}
