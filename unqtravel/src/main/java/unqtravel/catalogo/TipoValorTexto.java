package unqtravel.catalogo;

public class TipoValorTexto extends TipoValorDinamico{
	
	@Override
	public boolean esValido(String text) {
		return (text != null) && (!text.isBlank()); // Para evaluar el caso en el que texto recibido solo tenga espacios.
	}
}
