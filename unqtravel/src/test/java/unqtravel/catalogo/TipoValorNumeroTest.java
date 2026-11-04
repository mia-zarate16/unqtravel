package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoValorNumeroTest {
	
	private TipoValorNumero tipo;
	
	@BeforeEach
	public void setUp() {
		tipo = new TipoValorNumero();
	}
	
	@Test
	public void testNumeroPositivoValido() {
		//EXERCISE
		boolean resultado = tipo.esValido(9);
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testNumeroNegativoValido() {
		//EXERCISE
		boolean resultado = tipo.esValido(-9);
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testNumeroCeroValido() {
		//EXERCISE
		boolean resultado = tipo.esValido(0);
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testNuloInvalido() {
		//EXERCISE
		boolean resultado = tipo.esValido((Number) null);
		
		//VERIFY
		assertFalse(resultado);
	}
}
