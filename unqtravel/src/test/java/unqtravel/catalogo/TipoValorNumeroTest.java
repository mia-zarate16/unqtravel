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
    public void testConvertirNumeroPositivo() {
        // EXERCISE
        Object resultado = tipo.convertir("9");

        // VERIFY
        assertEquals(Double.valueOf(9), resultado);
    }
	
	
	@Test
    public void testConvertirNumeroNegativo() {
        // EXERCISE
        Object resultado = tipo.convertir("-9");

        // VERIFY
        assertEquals(Double.valueOf(-9), resultado);
    }
	
	@Test
    public void testConvertirCero() {
        // EXERCISE
        Object resultado = tipo.convertir("0");

        // VERIFY
        assertEquals(Double.valueOf(0), resultado);
    }
	
	@Test
    public void testConvertirNumeroDecimal() {
        // EXERCISE
        Object resultado = tipo.convertir("9.5");

        // VERIFY
        assertEquals(Double.valueOf(9.5), resultado);
    }
	
	@Test
    public void testConvertirTextoInvalido() {
        // EXERCISE Y VERIFY
        assertThrows(IllegalArgumentException.class, () -> {tipo.convertir("nueve");});
    }
}
