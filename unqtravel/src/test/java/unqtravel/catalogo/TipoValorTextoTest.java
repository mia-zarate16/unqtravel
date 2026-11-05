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
    public void testTextoValido() {
        // EXERCISE
        boolean resultado = tipo.esValido("Hotel Central");

        // VERIFY
        assertTrue(resultado);
    }
	
	@Test
    public void testTextoConEspaciosInvalido() {
        // EXERCISE
        boolean resultado = tipo.esValido("   ");

        // VERIFY
        assertFalse(resultado);
    }
	
	@Test
    public void testTextoNuloInvalido() {
		// EXERCISE
        boolean resultado = tipo.esValido((String) null);

        // VERIFY
        assertFalse(resultado);
    }
}
