package unqtravel.catalogo;

public class TipoValorBooleano extends TipoValorDinamico{
	
	@Override
	public boolean esValido(Boolean valor) {
		return valor != null;
	}
}
