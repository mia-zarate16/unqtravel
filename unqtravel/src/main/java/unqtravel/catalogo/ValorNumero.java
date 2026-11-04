package unqtravel.catalogo;

public class ValorNumero implements ValorDinamico{
	
	private Number valor;

	
	//								CONSTRUCTOR
	public ValorNumero(Number valor) {
		super();
		this.valor = valor;
	}
	
	
	//								MÉTODO PARA VALIDAR
	@Override
	public boolean esValidoPara(TipoValorDinamico tipo) {
		return tipo.esValido(valor);
	}
}
