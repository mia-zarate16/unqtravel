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
    public void testConvertirTrue() {
        // EXERCISE
        Object resultado = tipo.convertir("true");

        // VERIFY
        assertEquals(Boolean.TRUE, resultado);
    }
	
	@Test
    public void testConvertirFalse() {
        // EXERCISE
        Object resultado = tipo.convertir("false");

        // VERIFY
        assertEquals(Boolean.FALSE, resultado);
    }
	
	@Test
    public void testConvertirTrueEnMayusculas() {
        // EXERCISE
        Object resultado = tipo.convertir("TRUE");

        // VERIFY
        assertEquals(Boolean.TRUE, resultado);
    }
	
	@Test
    public void testConvertirTextoInvalido() {
        // EXERCISE - VERIFY
        assertThrows(IllegalArgumentException.class, () -> {tipo.convertir("quizas");});
    }
 
}
