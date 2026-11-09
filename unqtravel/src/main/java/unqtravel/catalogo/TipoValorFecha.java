package unqtravel.catalogo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TipoValorFecha implements TipoValorDinamico{
	
	@Override
    public LocalDate convertir(String valor) {
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "El valor ingresado no es una fecha válida.", e
            );
        }
    }
}
