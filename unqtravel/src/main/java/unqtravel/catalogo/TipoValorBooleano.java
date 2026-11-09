package unqtravel.catalogo;

public class TipoValorBooleano implements TipoValorDinamico{
	
	@Override
    public Boolean convertir(String valor) {
        if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException(
                "El valor ingresado no es un booleano."
            );
        }
        return Boolean.valueOf(valor);
    }
}
