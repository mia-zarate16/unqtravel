package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class ServicioIndividualTest {
	
	private AtributoDinamico capacidadV1;
	private AtributoDinamico capacidadV2;
	private AtributoDinamico ratingV1;
	private AtributoDinamico ratingV2;
	private AtributoDinamico politicaMV1;
	private ServicioIndividual servicioHotel;
	private ServicioIndividual servicioExcursion;
    private Proveedor operador;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    @BeforeEach
    public void setUp() {
        operador = mock(Proveedor.class);
        
        capacidadV1 = mock(AtributoDinamico.class);
        capacidadV2 = mock(AtributoDinamico.class);
        ratingV1 = mock(AtributoDinamico.class);
        ratingV2 = mock(AtributoDinamico.class);
        politicaMV1 = mock(AtributoDinamico.class);
        
        when(capacidadV1.getNombre()).thenReturn("CapacidadMaxima");
        when(capacidadV2.getNombre()).thenReturn("CapacidadMaxima");
        when(ratingV1.getNombre()).thenReturn("RatingEstrellas");
        when(ratingV2.getNombre()).thenReturn("RatingEstrellas");
        when(politicaMV1.getNombre()).thenReturn("PoliticaMascotas");

        servicioHotel = new ServicioIndividual("1", "NocheDeHotelCentral", operador,
        	    120.0, 0.10, "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO, 50);
        
        servicioExcursion = new ServicioIndividual("2", "ExcursionGuiadaMuseo", operador, 100.0, 0.10,
                "Excursion guiada al museo", CategoriaServicio.EXCURSION, 20);
        
        fechaInicio = LocalDate.of(2026, 11, 10);
        fechaFin = LocalDate.of(2026, 11, 12);
        
    }
	
	
    @Test
    public void testPermiteAgregarAtributo() {
        // EXERCISE
        servicioHotel.agregarAtributo(capacidadV1);

        // VERIFY
        assertTrue(servicioHotel.tieneAtributo("CapacidadMaxima"));
    }
	
	@Test
	public void testNoPermiteAgregarAtributosRepetidos() {
		//SETUP
		servicioHotel.agregarAtributo(capacidadV1);
		
		//EXERCICE Y VERIFY
		assertThrows(IllegalArgumentException.class, () -> servicioHotel.agregarAtributo(capacidadV2));
	}
	
	@Test
	public void testNoPermiteAgregarAtributoNulo() {

	    // EXERCISE Y VERIFY
	    assertThrows(NullPointerException.class, () -> servicioHotel.agregarAtributo(null));
	}
	
	@Test
	public void testElAtributoFueEliminado() {
		//SETUP
		servicioHotel.agregarAtributo(ratingV1);
		
		//EXERCISE
		servicioHotel.eliminarAtributo("RatingEstrellas");
		
		//VERIFY
		assertFalse(servicioHotel.tieneAtributo("RatingEstrellas"));
	}
	
	@Test
	public void testSeObtieneAtributo() {
		//SETUP
		servicioExcursion.agregarAtributo(politicaMV1);
		
		//EXERCISE
		AtributoDinamico resultante = servicioExcursion.obtenerAtributo("PoliticaMascotas");
		
		//VERIFY
		assertSame(politicaMV1, resultante);
	}
	
	@Test
	public void testSePuedeAsignarValorAlAtributo() {
		// SETUP
        servicioHotel.agregarAtributo(ratingV2);

        // EXERCISE
        servicioHotel.asignarValorAtributo(ratingV2, "25");

        // VERIFY
        verify(ratingV2).asignarValor("25");
	}
	
	@Test
    public void testNoSePuedeAsignarValorAlAtributo() {
        // SETUP
        servicioHotel.agregarAtributo(capacidadV1);

        // EXERCISE Y VERIFY
        assertThrows(IllegalArgumentException.class, () ->
            servicioHotel.asignarValorAtributo(capacidadV2, "9"));

        verify(capacidadV2, never()).asignarValor("9");
    }
	
	@Test
    public void testEsInvalidoElServicioPorNombreVacio() {
        // SETUP
        ServicioIndividual servicio = new ServicioIndividual("123", "    ", operador, 120.0, 0.10,
                "Excursion guiada al museo", CategoriaServicio.EXCURSION, 20);

        // EXERCISE
        boolean resultado = servicio.esValido();

        // VERIFY
        assertFalse(resultado);
    }
	
	@Test
    public void testEsValidoElServicio() {
        // EXERCISE
        boolean resultado = servicioExcursion.esValido();

        // VERIFY
        assertTrue(resultado);
    }
	
	@Test
	public void testPrecioFinalConDescuento() {
	    // EXERCISE
	    double resultado = servicioHotel.precioFinal();

	    // VERIFY
	    assertEquals(108.0, resultado, 0.001);
	}
	
	@Test
    public void testObtenerListaDeAtributos() {
        // SETUP
        servicioHotel.agregarAtributo(capacidadV1);
        servicioHotel.agregarAtributo(ratingV2);

        // EXERCISE
        List<AtributoDinamico> resultado =
            servicioHotel.getAtributosDinamicos();

        // VERIFY
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(capacidadV1));
        assertTrue(resultado.contains(ratingV2));
    }
	
	@Test
	public void testConstructorConProveedorNulo() {
	    // EXERCISE Y VERIFY
	    assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("3", "NocheDeHotelCentral", null, 120.0, 0.10,
                "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO, 50));
	}
	
	@Test
	public void testGetId() {
	    // EXERCISE
	    String resultado = servicioHotel.getId();

	    // VERIFY
	    assertEquals("1", resultado);
	}
	
	@Test
	public void testServicioConIdNulo() {
		// SETUP
	    ServicioIndividual servicio = new ServicioIndividual(null, "NocheDeHotelCentral", operador, 120.0, 0.10,
	            "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO, 50);

	    // EXERCISE
	    boolean resultado = servicio.esValidoID();

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testGetOperador() {
	    // EXERCISE
	    Proveedor resultado = servicioHotel.getOperador();

	    // VERIFY
	    assertSame(operador, resultado);
	}
	
	@Test
	public void testGetPrecioBase() {
	    // EXERCISE
	    double resultado = servicioHotel.getPrecioBase();

	    // VERIFY
	    assertEquals(120.0, resultado, 0.001);
	}
	
	@Test
	public void testGetDescuento() {
	    // EXERCISE
	    double resultado = servicioHotel.getDescuento();

	    // VERIFY
	    assertEquals(0.10, resultado, 0.001);
	}
	
	@Test
	public void testGetCategoria() {
	    // EXERCISE
	    Set<CategoriaServicio> resultado = servicioHotel.getCategoria();

	    // VERIFY
	    assertEquals(CategoriaServicio.ALOJAMIENTO, resultado);
	}
	
	@Test
	public void testGetNombre() {
	    // EXERCISE
	    String resultado = servicioHotel.getNombre();

	    // VERIFY
	    assertEquals("NocheDeHotelCentral", resultado);
	}
	
	@Test
	public void testGetDescripcion() {
	    // EXERCISE
	    String resultado = servicioHotel.getDescripcion();

	    // VERIFY
	    assertEquals("Noche en un hotel central", resultado);
	}
	
	@Test
    public void testGetCapacidadMaxima() {
		//EXERCISE
		int resultado = servicioHotel.getCapacidadMaxima();
		
		//VERIFY
        assertEquals(50, resultado);
    }
	
	@Test
    public void testHayDisponibilidadInicialmente() {
		//EXERCISE
		boolean resultado = servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 5);
		
		//VERIFY
		assertTrue(resultado);
    }
	
	@Test
    public void testNoHayDisponibilidadCuandoSeSuperaLaCapacidad() {
        //SETUP
		servicioHotel.ocuparCupos(fechaInicio, fechaFin, 48);
        
		//EXERCISE
		boolean resultado = servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 3);
		
		//VERIFY
		assertFalse(resultado);
    }
	
	@Test
    public void testSePuedenOcuparCupos() {
		//EXERCISE
        boolean resultado = servicioHotel.ocuparCupos(fechaInicio, fechaFin, 10);
        
        //VERIFY
        assertTrue(resultado);

        assertFalse(servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 41));
    }
	
	@Test
    public void testNoSePuedenOcuparCuposSinDisponibilidad() {
        //SETUP
		servicioHotel.ocuparCupos(fechaInicio, fechaFin, 48);
		
		//EXERCISE
        boolean resultado = servicioHotel.ocuparCupos(fechaInicio, fechaFin, 3);
        
        //VERIFY
        assertFalse(resultado);

        assertTrue(servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 2));
    }
	
	@Test
    public void testSePuedenLiberarCupos() {
        //SETUP
		servicioHotel.ocuparCupos(fechaInicio, fechaFin, 10);
		
		//EXERCISE
        servicioHotel.liberarCupos(fechaInicio, fechaFin, 10);
        
        //VERIFY
        assertTrue(servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 50));
    }
	
	@Test
    public void testNoSePuedenLiberarCuposNoOcupados() {
        //EXERCISE Y VERIFY
		assertThrows(IllegalArgumentException.class, () -> servicioHotel.liberarCupos(fechaInicio, fechaFin, 5));
    }
	
	@Test
    public void testValidarSolicitudConFechasNulas() {
        //EXERCISE Y VERIFY
		assertThrows(NullPointerException.class, () -> servicioHotel.hayDisponibilidad(null, fechaFin, 5));
    }
	
	@Test
    public void testValidarSolicitudConFechaInicioPosterior() {
        //EXERCISE Y VERIFY
		assertThrows(IllegalArgumentException.class, () -> servicioHotel.hayDisponibilidad(fechaFin, fechaInicio, 5));
    }
	
	@Test
    public void testValidarSolicitudConCantidadCero() {
        //EXERCISE Y VERIFY
		assertThrows(IllegalArgumentException.class, () -> servicioHotel.hayDisponibilidad(fechaInicio, fechaFin, 0));
    }
	
}
