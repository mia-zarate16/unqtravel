package unqtravel.catalogo;

public class ValorTexto implements ValorDinamico{
	
	private String valor;
	
	
	//									CONSTRUCTOR
	public ValorTexto(String valor) {
		super();
		this.valor = valor;
	}
	
	
	//									MÉTODO PARA VALIDAR
	@Override
	public boolean esValidoPara(TipoValorDinamico tipo) {
		return tipo.esValido(valor);
	}


	
}
