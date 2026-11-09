package unqtravel.catalogo;

public class TipoValorTexto implements TipoValorDinamico{
	
	@Override
	public Object convertir(String valor) {
	    if (valor == null || valor.isBlank()) {
	        throw new IllegalArgumentException("El texto no puede estar vacío.");
	    }
	    return valor;
	}
	
}
