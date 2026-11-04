package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValorBooleanoTest {
	
	private ValorBooleano valor;
	
	@BeforeEach
	public void setUp() {
		valor = new ValorBooleano(true);
	}
	
	@Test
	public void testValorBooleanoCompatibleConTipoBooleano() {
	    // SETUP
	    TipoValorBooleano tipo = new TipoValorBooleano();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testValorBooleanoIncompatibleConTipotexto() {
	    // SETUP
	    TipoValorTexto tipo = new TipoValorTexto();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorBooleanoIncompatibleConTipoNumero() {
	    // SETUP
	    TipoValorNumero tipo = new TipoValorNumero();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorBooleanoIncompatibleConTipoFecha() {
	    // SETUP
	    TipoValorFecha tipo = new TipoValorFecha();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
}
