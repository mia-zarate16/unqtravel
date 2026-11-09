package unqtravel.catalogo;

public class TipoValorNumero implements TipoValorDinamico{

	@Override
    public Double convertir(String valor) {
        try {
            return Double.valueOf(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "El valor ingresado no es un número.", e
            );
        }
    }
	
}
