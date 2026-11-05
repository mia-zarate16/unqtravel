package unqtravel.catalogo;

public class TipoValorNumero extends TipoValorDinamico{

	@Override
	public boolean esValido(Number numero) {
		return numero != null;
	}
	
}
