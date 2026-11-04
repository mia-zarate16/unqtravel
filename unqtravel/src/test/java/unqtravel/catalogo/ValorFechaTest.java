package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValorFechaTest {
	
	private ValorFecha valor;
	
	@BeforeEach
	public void setUp() {
		valor = new ValorFecha(LocalDate.of(2026, 11, 4));
	}
	
	@Test
	public void testValorFechaCompatibleConTipoFecha() {
	    // SETUP
	    TipoValorFecha tipo = new TipoValorFecha();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testValorFechaIncompatibleConTipotexto() {
	    // SETUP
	    TipoValorTexto tipo = new TipoValorTexto();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorFechaIncompatibleConTipoNumero() {
	    // SETUP
	    TipoValorNumero tipo = new TipoValorNumero();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testValorFechaIncompatibleConTipoBooleano() {
	    // SETUP
	    TipoValorBooleano tipo = new TipoValorBooleano();

	    // EXERCISE
	    boolean resultado = valor.esValidoPara(tipo);

	    // VERIFY
	    assertFalse(resultado);
	}
}
