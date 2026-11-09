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
    public void testConvertirFechaValida() {
        // EXERCISE
        Object resultado = tipo.convertir("2026-11-04");

        // VERIFY
        assertEquals(LocalDate.of(2026, 11, 4), resultado);
    }
	
	@Test
    public void testConvertirFechaPasada() {
        // EXERCISE
        Object resultado = tipo.convertir("2001-08-25");

        // VERIFY
        assertEquals(LocalDate.of(2001, 8, 25), resultado);
    }
	
	@Test
    public void testConvertirFechaFutura() {
        // EXERCISE
        Object resultado = tipo.convertir("2030-12-15");

        // VERIFY
        assertEquals(LocalDate.of(2030, 12, 15), resultado);
    }
	
	@Test
    public void testConvertirFormatoInvalido() {
        // EXERCISE Y VERIFY
        assertThrows(IllegalArgumentException.class, () -> {tipo.convertir("04/11/2026");});
    }
	
	@Test
    public void testConvertirFechaInexistente() {
        // EXERCISE - VERIFY
        assertThrows(IllegalArgumentException.class, () -> {tipo.convertir("2026-02-30");});
    }
}
