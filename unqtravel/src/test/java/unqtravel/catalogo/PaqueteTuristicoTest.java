package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	    when(hotel.esValido()).thenReturn(true);
	    when(excursion.esValido()).thenReturn(true);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    assertTrue(paqueteV1.esValido());
	    
	    verify(hotel).esValido();
	    verify(excursion).esValido();

	    when(traslado.esValido()).thenReturn(false);
	    
	    paqueteV1.agregarItem(traslado);
	    
	    assertFalse(paqueteV1.esValido());
	    
	    verify(traslado).esValido();
	}
	
	@Test
	void testHayDisponibilidadConTodosLosItemsDisponibles() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);

	    when(hotel.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    assertTrue(paqueteV1.hayDisponibilidad(inicio, fin, 2));

	    verify(hotel).hayDisponibilidad(inicio, fin, 2);
	    verify(excursion).hayDisponibilidad(inicio, fin, 2);
	}

	@Test
	void testHayDisponibilidadConAlgunItemSinDisponibilidad() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);

	    when(hotel.hayDisponibilidad(inicio, fin, 2)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, 2)).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);
	    
	    assertFalse(paqueteV1.hayDisponibilidad(inicio, fin, 2));

	    verify(hotel).hayDisponibilidad(inicio, fin, 2);
	    verify(excursion).hayDisponibilidad(inicio, fin, 2);
	}

	@Test
	void testHayDisponibilidadEnPaqueteSinItems() {
	    assertTrue(paqueteV1.hayDisponibilidad(LocalDate.of(2026, 12, 1),LocalDate.of(2026, 12, 5),2));
	}
	
	@Test
	void testGetCategoria() {
	    when(hotel.getCategoria()).thenReturn(Set.of(CategoriaServicio.ALOJAMIENTO));
	    when(excursion.getCategoria()).thenReturn(Set.of(CategoriaServicio.EXCURSION));
	    when(traslado.getCategoria()).thenReturn(Set.of(CategoriaServicio.ALOJAMIENTO));

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);
	    paqueteV1.agregarItem(traslado);

	    Set<CategoriaServicio> categorias = paqueteV1.getCategoria();

	    assertEquals(2, categorias.size());
	    assertTrue(categorias.contains(CategoriaServicio.ALOJAMIENTO));
	    assertTrue(categorias.contains(CategoriaServicio.EXCURSION));
	}
	
	@Test
	void testGetCategoriaPaqueteVacio() {
	    assertTrue(paqueteV1.getCategoria().isEmpty());
	}
	
	@Test
	void testOcuparCuposCorrectamente() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);
	    int cantidad = 2;

	    when(hotel.hayDisponibilidad(inicio, fin, cantidad)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, cantidad)).thenReturn(true);

	    when(hotel.ocuparCupos(inicio, fin, cantidad)).thenReturn(true);
	    when(excursion.ocuparCupos(inicio, fin, cantidad)).thenReturn(true);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    assertTrue(paqueteV1.ocuparCupos(inicio, fin, cantidad));

	    verify(hotel).ocuparCupos(inicio, fin, cantidad);
	    verify(excursion).ocuparCupos(inicio, fin, cantidad);
	}
	
	@Test
	void testOcuparCuposSinDisponibilidad() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);
	    int cantidad = 2;

	    when(hotel.hayDisponibilidad(inicio, fin, cantidad)).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    assertFalse(paqueteV1.ocuparCupos(inicio, fin, cantidad));

	    verify(hotel, never()).ocuparCupos(inicio, fin, cantidad);
	    verify(excursion, never()).ocuparCupos(inicio, fin, cantidad);
	}
	
	@Test
	void testOcuparCuposFallaYLiberaLosAnteriores() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);
	    int cantidad = 2;

	    when(hotel.hayDisponibilidad(inicio, fin, cantidad)).thenReturn(true);
	    when(excursion.hayDisponibilidad(inicio, fin, cantidad)).thenReturn(true);

	    when(hotel.ocuparCupos(inicio, fin, cantidad)).thenReturn(true);
	    when(excursion.ocuparCupos(inicio, fin, cantidad)).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    assertFalse(paqueteV1.ocuparCupos(inicio, fin, cantidad));

	    verify(hotel).liberarCupos(inicio, fin, cantidad);
	    verify(excursion, never()).liberarCupos(inicio, fin, cantidad);
	}
	
	@Test
	void testOcuparCuposPaqueteVacio() {
	    assertTrue(paqueteV1.ocuparCupos(LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 5), 2));
	}
	
	@Test
	void testLiberarCupos() {
	    LocalDate inicio = LocalDate.of(2026, 12, 1);
	    LocalDate fin = LocalDate.of(2026, 12, 5);
	    int cantidad = 2;

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    paqueteV1.liberarCupos(inicio, fin, cantidad);

	    verify(hotel).liberarCupos(inicio, fin, cantidad);
	    verify(excursion).liberarCupos(inicio, fin, cantidad);
	}
	
	@Test
	void testLiberarCuposPaqueteVacio() {
	    assertDoesNotThrow(() -> paqueteV1.liberarCupos(LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 5), 2));
	}
	
	@Test
	public void testPaqueteConTodosSusItemsInvalidos() {
	    when(hotel.esValido()).thenReturn(false);
	    when(excursion.esValido()).thenReturn(false);
	    when(traslado.esValido()).thenReturn(false);

	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);
	    paqueteV1.agregarItem(traslado);

	    boolean resultado = paqueteV1.esValido();

	    assertFalse(resultado);
	    
	    verify(hotel).esValido();
	    verify(excursion, never()).esValido();
	    verify(traslado, never()).esValido();
	}
	
	@Test
	public void testPaqueteSinItems() {
	    boolean resultado = paqueteV1.esValido();

	    assertTrue(resultado);
	}
	
	@Test
	public void testAgregarItemAlPaquete() {
	    paqueteV1.agregarItem(hotel);

	    assertTrue(paqueteV1.contieneItem(hotel));
	}
	
	@Test
	public void testAgregarPaqueteAElMismo() {
	    assertThrows(IllegalArgumentException.class, () -> paqueteV2.agregarItem(paqueteV2));
	}
	
	@Test
	public void testEliminarItemDelPaquete() {
		paqueteV1.agregarItem(excursion);
		
		paqueteV1.eliminarItem(excursion);

	    assertFalse(paqueteV1.contieneItem(excursion));
	}
	
	@Test
	public void testPaqueteConOtroPaqueteInvalido() {
		when(hotel.esValido()).thenReturn(true);
		when(excursion.esValido()).thenReturn(false);
		
		paqueteV2.agregarItem(excursion);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		boolean resultado = paqueteV1.esValido();

	    assertFalse(resultado);
	    
	    verify(hotel).esValido();
	    verify(excursion).esValido();
	}
	
	@Test
	public void testPaqueteConOtroPaqueteValido() {
		when(hotel.esValido()).thenReturn(true);
		when(traslado.esValido()).thenReturn(true);
		
		paqueteV2.agregarItem(traslado);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		boolean resultado = paqueteV1.esValido();

	    assertTrue(resultado);
	    
	    verify(hotel).esValido();
	    verify(traslado).esValido();
	}
	
	@Test
	public void testPrecioDelPaquete() {
		when(hotel.precioFinal()).thenReturn(15000.0);
		when(excursion.precioFinal()).thenReturn(4000.0);
		when(traslado.precioFinal()).thenReturn(2000.0);
		
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(excursion);
		paqueteV1.agregarItem(traslado);
		
		double resultado = paqueteV1.precioFinal();

		assertEquals(18900.0, resultado, 0.001);
	    
	    verify(hotel).precioFinal();
	    verify(excursion).precioFinal();
	    verify(traslado).precioFinal();
	}
	
	@Test
	public void testPrecioDelPaqueteQueTieneUnPaquete() {
		when(hotel.precioFinal()).thenReturn(15000.0);
		when(excursion.precioFinal()).thenReturn(4000.0);
		when(traslado.precioFinal()).thenReturn(2000.0);
		
		paqueteV2.agregarItem(excursion);
		paqueteV2.agregarItem(traslado);
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		double resultado = paqueteV1.precioFinal();

		assertEquals(17820.0, resultado, 0.001);
	    
	    verify(hotel).precioFinal();
	    verify(excursion).precioFinal();
	    verify(traslado).precioFinal();
	}
	
	@Test
	public void testCantidadDeItemsDelPaquete() {
		paqueteV1.agregarItem(excursion);
		paqueteV1.agregarItem(traslado);
		paqueteV1.agregarItem(hotel);
		paqueteV1.agregarItem(paqueteV2);
		
		int resultado = paqueteV1.cantidadItems();

		assertEquals(4, resultado);
	}
	
	@Test
	public void testObtenerItemDelPaquete() {
		paqueteV2.agregarItem(traslado);
		paqueteV2.agregarItem(hotel);
		paqueteV2.agregarItem(excursion);
		
		EntidadCatalogo resultado = paqueteV2.obtenerItem(2);

		assertEquals(excursion, resultado);
	}
	
	@Test
	public void testEstaVacioElPaquete() {
		boolean resultado = paqueteV1.estaVacio();

		assertTrue(resultado);
	}
	
	@Test
	public void testNoEstaVacioElPaquete() {
		paqueteV2.agregarItem(excursion);
		
		boolean resultado = paqueteV2.estaVacio();

		assertFalse(resultado);
	}
	
	@Test
	public void testGetDescuento() {
	    double resultado = paqueteV1.getDescuento();

	    assertEquals(0.10, resultado, 0.001);
	}
	
	@Test
	public void testGetNombre() {
	    String resultado = paqueteV1.getNombre();

	    assertEquals("EscapadaUrbana", resultado);
	}
	
	@Test
	public void testGetDescripcion() {
	    String resultado = paqueteV1.getDescripcion();

	    assertEquals("Escapada urbana de 3 dias.", resultado);
	}
	
	@Test
	public void testGetItems() {
	    paqueteV1.agregarItem(hotel);
	    paqueteV1.agregarItem(excursion);

	    List<EntidadCatalogo> resultado = paqueteV1.getItems();

	    assertEquals(2, resultado.size());
	    assertTrue(resultado.contains(hotel));
	    assertTrue(resultado.contains(excursion));
	}
	
	@Test
	void testObtenerServiciosIndividuales() {
	    ServicioIndividual servicio1 = mock(ServicioIndividual.class);
	    ServicioIndividual servicio2 = mock(ServicioIndividual.class);

	    when(servicio1.obtenerServiciosIndividuales()).thenReturn(Set.of(servicio1));
	    when(servicio2.obtenerServiciosIndividuales()).thenReturn(Set.of(servicio2));

	    paqueteV1.agregarItem(servicio1);
	    paqueteV1.agregarItem(servicio2);

	    Set<ServicioIndividual> servicios = paqueteV1.obtenerServiciosIndividuales();

	    assertEquals(2, servicios.size());
	    assertTrue(servicios.contains(servicio1));
	    assertTrue(servicios.contains(servicio2));
	}
}
