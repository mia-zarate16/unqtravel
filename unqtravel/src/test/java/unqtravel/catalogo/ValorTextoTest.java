package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValorTextoTest {
	
	private ValorTexto valor;
	
	@BeforeEach
	public void setUp() {
		valor = new ValorTexto("Hotel Central");
	}
	
	@Test
	public void testValorTextoCompatibleConTipoTexto() {
	    // SETUP
	    TipoValorTexto tipo = new TipoValorTexto();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testValorTextoIncompatibleConTipoNumero() {
	    // SETUP
	    TipoValorNumero tipo = new TipoValorNumero();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}

	@Test
	public void testValorTextoIncompatibleConTipoBooleano() {
	    // SETUP
	    TipoValorBooleano tipo = new TipoValorBooleano();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}

	@Test
	public void testValorTextoIncompatibleConTipoFecha() {
	    // SETUP
	    TipoValorFecha tipo = new TipoValorFecha();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
}
