package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValorNumeroTest {

	private ValorNumero valor;
	
	@BeforeEach
	public void setUp() {
		valor = new ValorNumero(25);
	}
	
	@Test
	public void testValorNumeroCompatibleConTipoNumero() {
	    // SETUP
	    TipoValorNumero tipo = new TipoValorNumero();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testValorNumeroIncompatibleConTipotexto() {
	    // SETUP
	    TipoValorTexto tipo = new TipoValorTexto();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorNumeroIncompatibleConTipoBooleano() {
	    // SETUP
	    TipoValorBooleano tipo = new TipoValorBooleano();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorNumeroIncompatibleConTipoFecha() {
	    // SETUP
	    TipoValorFecha tipo = new TipoValorFecha();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
}
