package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoValorBooleanoTest {
	
	private TipoValorBooleano tipo;
	
	@BeforeEach
	public void setUp() {
		tipo = new TipoValorBooleano();
	}
	
	@Test
	public void testBooleanoTrueValido() {
		//EXERCISE
		boolean resultado = tipo.esValido(true);
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testBooleanoFalseValido() {
		//EXERCISE
		boolean resultado = tipo.esValido(false);
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testBooleanoNuloInvalido() {
		//EXERCISE
		boolean resultado = tipo.esValido((Boolean) null);
		
		//VERIFY
		assertFalse(resultado);
	}
 
}
