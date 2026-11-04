package unqtravel.catalogo;

import java.time.LocalDate;

public class ValorFecha implements ValorDinamico{
	
	private LocalDate valor;

	
	//									CONSTRUCTOR
	public ValorFecha(LocalDate valor) {
		super();
		this.valor = valor;
	}
	
	
	//									MÉTODO PARA VALIDAR
	public boolean esValidoPara(TipoValorDinamico tipo) {
		return tipo.esValido(valor);
	}
}
