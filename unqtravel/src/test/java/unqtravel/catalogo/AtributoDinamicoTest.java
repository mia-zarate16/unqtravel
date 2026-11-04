package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AtributoDinamicoTest {
	
	private AtributoDinamico capacidadObligatorio;
	private AtributoDinamico capacidadNoObligatorio;
	private AtributoDinamico ratingObligatorio;
	private AtributoDinamico ratingNoObligatorio;
	private AtributoDinamico politicaMObligatoria;
	private AtributoDinamico fechaSalida;
	
	@BeforeEach
	public void setUp() {
		capacidadObligatorio = new AtributoDinamico("CapacidadMaxima", true, new TipoValorNumero());
		capacidadNoObligatorio = new AtributoDinamico("CapacidadMaxima", false, new TipoValorNumero());
		ratingObligatorio = new AtributoDinamico("RatingEstrellas", true, new TipoValorNumero());
		ratingNoObligatorio = new AtributoDinamico("RatingEstrellas", false, new TipoValorNumero());
		politicaMObligatoria = new AtributoDinamico ("PoliticaMascota", true, new TipoValorBooleano());
		fechaSalida = new AtributoDinamico("FechaDeSalida", true, new TipoValorFecha());
	}
	
	@Test
	public void testAtributoNumericoValido() {
		//SETUP
		capacidadObligatorio.asignarValor(new ValorNumero(9));
		
		//EXERCISE
		boolean resultado = capacidadObligatorio.esValido();
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testAtributoNumericoInvalido() {
		//SETUP
		capacidadObligatorio.asignarValor(new ValorTexto("nueve"));
		
		//EXERCISE
		boolean resultado = capacidadObligatorio.esValido();
		
		//VERIFY
		assertFalse(resultado);
		}
	
	@Test
	public void testAtributoObligatorioSinValorAsignado() {
		//EXERSICE
		boolean resultado = ratingObligatorio.esValido();
		
		//VERIFY
		assertFalse(resultado);
	}
	
	@Test
	public void testAtributoNoObligatorioSinValorAsignado() {
		//EXERCISE
		boolean resultado = ratingNoObligatorio.esValido();
		
		//VERIFY
		assertTrue(resultado);
		}
	
	@Test
	public void testAtributoObligatorioConValorAsignado() {
		//SETUP
		politicaMObligatoria.asignarValor(new ValorBooleano(false));
		
		//EXERCISE
		boolean resultado = politicaMObligatoria.esValido();
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testAtributoNoObligatorioConValorIncompatible() {
		// SETUP
	    capacidadNoObligatorio.asignarValor(new ValorTexto("veinte"));

	    // EXERCISE
	    boolean resultado = capacidadNoObligatorio.esValido();

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testAtributoBooleanoObligatorioConValorFalso() {
		// SETUP
		politicaMObligatoria.asignarValor(new ValorBooleano(false));

	    // EXERCISE
	    boolean resultado = politicaMObligatoria.esValido();

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testAtributoFechaObligatorioConValorValido() {
		// SETUP
		fechaSalida.asignarValor(new ValorFecha(LocalDate.of(2026, 12, 15)));

	    // EXERCISE
	    boolean resultado = fechaSalida.esValido();

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testIsEsObligatorio() {
		//EXERCISE
		boolean resultado = capacidadObligatorio.isEsObligatorio();
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testGetTipoValor() {
		// SETUP
	    TipoValorNumero tipoN = new TipoValorNumero();
	    
	    AtributoDinamico ratingN = new AtributoDinamico("RatingEstrellas", true, tipoN);

	    // EXERCISE
	    TipoValorDinamico resultado = ratingN.getTipoValor();

	    // VERIFY
	    assertSame(tipoN, resultado);
	}
	
	@Test
	public void testGetNombre() {
		// EXERCISE
	    String resultado = politicaMObligatoria.getNombre();

	    // VERIFY
	    assertEquals("PoliticaMascota", resultado);
	}
	
	@Test
	public void testGetValor() {
		//SETUP
		ValorFecha nacimiento = new ValorFecha(LocalDate.of(2001, 8, 25));
		fechaSalida.asignarValor(nacimiento);
		
		// EXERCISE
	    ValorDinamico resultado = fechaSalida.getValor();

	    // VERIFY
	    assertEquals(nacimiento, resultado);
	}
	
}
