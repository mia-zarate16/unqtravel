package unqtravel.catalogo;

import java.time.LocalDate;

public class TipoValorDinamico {
	
	public boolean esValido(String text) {
		return false;
	}
	
	public boolean esValido(Number numero) {
		return false;
	}
	
	public boolean esValido(Boolean valor) {
		return false;
	}
	
	public boolean esValido(LocalDate date) {
		return false;
	}
}
