package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class PaqueteTuristicoTest {
	
	private PaqueteTuristico paqueteV1;
	private PaqueteTuristico paqueteV2;
	private EntidadCatalogo hotel;
	private EntidadCatalogo excursion;
	private EntidadCatalogo traslado;
	
	@BeforeEach
    public void setUp() {
		
        paqueteV1 = new PaqueteTuristico(0.10, "EscapadaUrbana", "Escapada urbana de 3 dias.");
        paqueteV2 = new PaqueteTuristico(0.20, "RecorridoEnMuseos", "Recorrido por varios museos de la ciudad.");
        hotel = mock(EntidadCatalogo.class);
        excursion = mock(EntidadCatalogo.class);
        traslado = mock(EntidadCatalogo.class);
        
    }
	
	
	@Test
	public void testPaqueteConTodosSusItemsValidos() {
	    //SETUP
	    when(hotel.esValido()).thenReturn(true);
	    when(excursion.esValido()).thenReturn(true);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    //EXERCISE
	    boolean resultado = paqueteV1.esValido();

	    //VERIFY
	    assertTrue(resultado);
	    
	    verify(hotel).esValido();
	    verify(excursion).esValido();
	}
	
	@Test
	public void testPaqueteConAlgunoDeSusItemsInvalido() {
		//SETUP
		when(hotel.esValido()).thenReturn(true);
	    when(traslado.esValido()).thenReturn(false);
	    
	    paqueteV2.agregarItem(hotel);
	    paqueteV2.agregarItem(traslado);
	    
	    //EXERCISE
	    boolean resultado = paqueteV2.esValido();
	    
	    //VERIFY
	    assertFalse(resultado);
	    
	    verify(hotel).esValido();
	    verify(traslado).esValido();
	}
	
	@Test
	void testHayDisponibilidadConTodosLosItemsDisponibles() {
	    // SETUP
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);

	    when(hotel.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    // VERIFY
	    assertTrue(paqueteV1.hayDisponibilidad(inicio, fin, 2));

	    verify(hotel).hayDisponibilidad(inicio, fin, 2);
	    verify(excursion).hayDisponibilidad(inicio, fin, 2);
	}

	@Test
	void testHayDisponibilidadConAlgunItemSinDisponibilidad() {
	    // SETUP
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);

	    when(hotel.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, 2)).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);
	    
	    // VERIFY
	    assertFalse(paqueteV1.hayDisponibilidad(inicio, fin, 2));

	    verify(hotel).hayDisponibilidad(inicio, fin, 2);
	    verify(excursion).hayDisponibilidad(inicio, fin, 2);
	}

	@Test
	void testHayDisponibilidadEnPaqueteSinItems() {
	    // VERIFY
	    assertTrue(paqueteV1.hayDisponibilidad(LocalDate.of(2026, 12, 1),LocalDate.of(2026, 12, 5),2));
	}
	
	@Test
	void testCategoriasCorrectas() {
		
	}
	
	@Test
	public void testPaqueteConTodosSusItemsInvalidos() {
	    //SETUP
	    when(hotel.esValido()).thenReturn(false);
	    when(excursion.esValido()).thenReturn(false);
	    when(traslado.esValido()).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);
	    paqueteV1.agregarItem(traslado);

	    //EXERCISE
	    boolean resultado = paqueteV1.esValido();

	    //VERIFY
	    assertFalse(resultado);
	    
	    verify(hotel).esValido();
	    verify(excursion, never()).esValido();
	    verify(traslado, never()).esValido();
	}
	
	@Test
	public void testPaqueteSinItems() {
	    // EXERCISE
	    boolean resultado = paqueteV1.esValido();

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testAgregarItemAlPaquete() {
		// EXERCISE
	    paqueteV1.agregarItem(hotel);

	    // VERIFY
	    assertTrue(paqueteV1.contieneItem(hotel));
	}
	
	@Test
	public void testAgregarPaqueteAElMismo() {
	    // EXERCISE Y VERIFY
	    assertThrows(IllegalArgumentException.class, () -> paqueteV2.agregarItem(paqueteV2));
	}
	
	@Test
	public void testEliminarItemDelPaquete() {
		//SETUP
		paqueteV1.agregarItem(excursion);
		
		// EXERCISE
		paqueteV1.eliminarItem(excursion);

	    // VERIFY
	    assertFalse(paqueteV1.contieneItem(excursion));
	}
	
	@Test
	public void testPaqueteConOtroPaqueteInvalido() {
		//SETUP
		when(hotel.esValido()).thenReturn(true);
		when(excursion.esValido()).thenReturn(false);
		
		paqueteV2.agregarItem(excursion);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		// EXERCISE
		boolean resultado = paqueteV1.esValido();

	    // VERIFY
	    assertFalse(resultado);
	    
	    verify(hotel).esValido();
	    verify(excursion).esValido();
	}
	
	@Test
	public void testPaqueteConOtroPaqueteValido() {
		//SETUP
		when(hotel.esValido()).thenReturn(true);
		when(traslado.esValido()).thenReturn(true);
		
		paqueteV2.agregarItem(traslado);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		// EXERCISE
		boolean resultado = paqueteV1.esValido();

	    // VERIFY
	    assertTrue(resultado);
	    
	    verify(hotel).esValido();
	    verify(traslado).esValido();
	}
	
	@Test
	public void testPrecioDelPaquete() {
		//SETUP
		when(hotel.precioFinal()).thenReturn(15000.0);
		when(excursion.precioFinal()).thenReturn(4000.0);
		when(traslado.precioFinal()).thenReturn(2000.0);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(excursion);
		paqueteV1.agregarItem(traslado);
		
		// EXERCISE
		double resultado = paqueteV1.precioFinal();

	    // VERIFY
		assertEquals(18900.0, resultado, 0.001);
	    
	    verify(hotel).precioFinal();
	    verify(excursion).precioFinal();
	    verify(traslado).precioFinal();
	}
	
	@Test
	public void testPrecioDelPaqueteQueTieneUnPaquete() {
		//SETUP
		when(hotel.precioFinal()).thenReturn(15000.0);
		when(excursion.precioFinal()).thenReturn(4000.0);
		when(traslado.precioFinal()).thenReturn(2000.0);
		
		paqueteV2.agregarItem(excursion);
		paqueteV2.agregarItem(traslado);
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		// EXERCISE
		double resultado = paqueteV1.precioFinal();

	    // VERIFY
		assertEquals(17820.0, resultado, 0.001);
	    
	    verify(hotel).precioFinal();
	    verify(excursion).precioFinal();
	    verify(traslado).precioFinal();
	}
	
	@Test
	public void testCantidadDeItemsDelPaquete() {
		//SETUP
		paqueteV1.agregarItem(excursion);
		paqueteV1.agregarItem(traslado);
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		// EXERCISE
		int resultado = paqueteV1.cantidadItems();

	    // VERIFY
		assertEquals(4, resultado);
	}
	
	@Test
	public void testObtenerItemDelPaquete() {
		//SETUP
		paqueteV2.agregarItem(traslado);
		paqueteV2.agregarItem(hotel);
		paqueteV2.agregarItem(excursion);
		
		// EXERCISE
		EntidadCatalogo resultado = paqueteV2.obtenerItem(2);

	    // VERIFY
		assertEquals(excursion, resultado);
	}
	
	@Test
	public void testEstaVacioElPaquete() {
		// EXERCISE
		boolean resultado = paqueteV1.estaVacio();

	    // VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testNoEstaVacioElPaquete() {
		//SETUP
		paqueteV2.agregarItem(excursion);
		
		// EXERCISE
		boolean resultado = paqueteV2.estaVacio();

	    // VERIFY
		assertFalse(resultado);
	}
	
	@Test
	public void testGetDescuento() {
	    // EXERCISE
	    double resultado = paqueteV1.getDescuento();

	    // VERIFY
	    assertEquals(0.10, resultado, 0.001);
	}
	
	@Test
	public void testGetNombre() {
	    // EXERCISE
	    String resultado = paqueteV1.getNombre();

	    // VERIFY
	    assertEquals("EscapadaUrbana", resultado);
	}
	
	@Test
	public void testGetDescripcion() {
	    // EXERCISE
	    String resultado = paqueteV1.getDescripcion();

	    // VERIFY
	    assertEquals("Escapada urbana de 3 dias.", resultado);
	}
	
	@Test
	public void testGetItems() {
	    // SETUP
	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    // EXERCISE
	    List<EntidadCatalogo> resultado = paqueteV1.getItems();

	    // VERIFY
	    assertEquals(2, resultado.size());
	    assertTrue(resultado.contains(hotel));
	    assertTrue(resultado.contains(excursion));
	}
}
