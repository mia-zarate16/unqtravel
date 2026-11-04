package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoValorFechaTest {

	private TipoValorFecha tipo;
	
	@BeforeEach
	public void setUp() {
		tipo = new TipoValorFecha();
	}
	
	@Test
	public void testFechaHoyValida() {
		//EXERCISE
		boolean resultado = tipo.esValido(LocalDate.of(2026, 11, 4));
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testFechaPasadaValida() {
		//EXERCISE
		boolean resultado = tipo.esValido(LocalDate.of(2001, 8, 25));
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testFechaNulaInvalida() {
		//EXERCISE
		boolean resultado = tipo.esValido((LocalDate) null);
		
		//VERIFY
		assertFalse(resultado);
	}
}
