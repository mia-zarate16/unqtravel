package unqtravel.catalogo;

import java.time.LocalDate;

public class TipoValorFecha extends TipoValorDinamico{
	
	@Override
	public boolean esValido(LocalDate date) {
		return date != null;
	}
}
