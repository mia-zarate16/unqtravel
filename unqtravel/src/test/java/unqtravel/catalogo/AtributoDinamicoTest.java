package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AtributoDinamicoTest {
	
	private AtributoDinamico capacidadObligatorio;
	private AtributoDinamico politicaMObligatoria;
	private AtributoDinamico fechaSalida;
	
	@BeforeEach
	public void setUp() {
		capacidadObligatorio = new AtributoDinamico("CapacidadMaxima", true, new TipoValorNumero(), "9");
		politicaMObligatoria = new AtributoDinamico ("PoliticaMascota", true, new TipoValorBooleano(), "false");
		fechaSalida = new AtributoDinamico("FechaDeSalida", true, new TipoValorFecha(), "2026-12-15");
	}
	
	@Test
	public void testAtributoNumericoValido() {
		//EXERCISE
		Object resultado = capacidadObligatorio.getValor();
		
		//VERIFY
		assertEquals(Double.valueOf(9), resultado);
	}
	
	@Test
	public void testAtributoNumericoInvalido() {
		// EXERCISE Y VERYFY
        assertThrows(IllegalArgumentException.class, 
        		() -> new AtributoDinamico("CapacidadMaxima", true, new TipoValorNumero(), "nueve"));
	}
	
	@Test
    public void testAsignarNuevoValorNumerico() {
        // EXERCISE
        capacidadObligatorio.asignarValor("15");

        // VERIFY
        assertEquals(Double.valueOf(15), capacidadObligatorio.getValor());
    }
	
	@Test
    public void testNoAsignarValorNumericoIncompatible() {
        // EXERCISE
        assertThrows(IllegalArgumentException.class, 
        		() -> capacidadObligatorio.asignarValor("veinte"));

        // VERIFY
        assertEquals(Double.valueOf(9), capacidadObligatorio.getValor());
    }
	
	@Test
    public void testAtributoBooleanoConValorFalso() {
		// EXERCISE
        Object resultado = politicaMObligatoria.getValor();

        // VERIFY
        assertEquals(Boolean.FALSE, resultado);
    }
	
	@Test
    public void testAtributoBooleanoInvalido() {
		// EXERCISE Y VERIFY
        assertThrows(IllegalArgumentException.class, 
        		() -> new AtributoDinamico("PoliticaMascota", true, new TipoValorBooleano(), "quizas"));
    }
	
	@Test
    public void testAtributoFechaValido() {
		// EXERCISE
        Object resultado = fechaSalida.getValor();

        // VERIFY
        assertEquals(LocalDate.of(2026, 12, 15), resultado);
    }
	
	@Test
    public void testAtributoFechaInvalido() {
		// EXERCISE Y VERIFY
        assertThrows(IllegalArgumentException.class, 
        		() -> new AtributoDinamico("FechaDeSalida", true, new TipoValorFecha(), "15/12/2026"));
    }
	
	@Test
    public void testAtributoTextoValido() {
		// SETUP
        AtributoDinamico destino = new AtributoDinamico("Destino", true, new TipoValorTexto(), "Argentina");

        // EXERCISE
        Object resultado = destino.getValor();

        // VERIFY
        assertEquals("Argentina", resultado);
    }
	
	@Test
    public void testConstructorRechazaValorNulo() {
		// EXERCISE Y VERIFY
        assertThrows(NullPointerException.class, 
        		() -> new AtributoDinamico("CapacidadMaxima", true, new TipoValorNumero(), null));
    }
	
	@Test
	public void testIsObligatorio() {
		//EXERCISE
		boolean resultado = capacidadObligatorio.isObligatorio();
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testGetTipoValor() {
		// SETUP
	    TipoValorNumero tipoN = new TipoValorNumero();
	    
	    AtributoDinamico ratingN = new AtributoDinamico("RatingEstrellas", true, tipoN, "5");

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
		// EXERCISE
        Object resultado = fechaSalida.getValor();

        // VERIFY
        assertEquals(LocalDate.of(2026, 12, 15), resultado);
	}
	
}
