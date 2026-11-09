package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoValorTextoTest {
	
	private TipoValorTexto tipo;
	
	@BeforeEach
	public void setUp() {
		tipo = new TipoValorTexto();
	}
	
	@Test
    public void testConvertirTextoValido() {
        // EXERCISE
        Object resultado = tipo.convertir("Hotel Central");

        // VERIFY
        assertEquals("Hotel Central", resultado);
    }
	
	@Test
    public void testConvertirTextoConEspaciosInternos() {
        // EXERCISE
        Object resultado = tipo.convertir("Hotel Gran Central");

        // VERIFY
        assertEquals("Hotel Gran Central", resultado);
    }
	
	@Test
    public void testConvertirTextoConEspaciosEnBlanco() {
        // EXERCISE - VERIFY
        assertThrows(IllegalArgumentException.class, () -> {tipo.convertir("   ");});
    }
}
