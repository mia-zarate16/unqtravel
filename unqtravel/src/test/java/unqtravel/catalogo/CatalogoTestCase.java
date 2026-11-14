package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import unqtravel.Busqueda.*;

public class CatalogoTestCase {
  //SUT
  private Catalogo catTest;
  private CriterioNombre c1;
  private CriterioCategoria c2;
  private CriterioDisponibilidad c3;
  private CriterioPrecio c4;
  private CriterioAND cA;
  private CriterioOR cO;
  private CriterioNOT cN;
  
  //DOC
  private ServicioIndividual e1;
  private ServicioIndividual e2;
  private ServicioIndividual e3;
  private ServicioIndividual e4;
  private Set<CategoriaServicio> setCat1;
  private Set<CategoriaServicio> setCat2;
  private Set<CategoriaServicio> setCat3;
	
  @BeforeEach
  void setUp() {
	//DOC
	e1 = mock(ServicioIndividual.class);
	e2 = mock(ServicioIndividual.class);
	e3 = mock(ServicioIndividual.class);
	e4 = mock(ServicioIndividual.class);
	  
	//SUT
	catTest = new Catalogo();
	catTest.agregarAlCatalogo(e1); catTest.agregarAlCatalogo(e2);
	catTest.agregarAlCatalogo(e3); catTest.agregarAlCatalogo(e4); 
	
	c1 = new CriterioNombre("Nombre Generico");
	c2 = new CriterioCategoria(CategoriaServicio.ALOJAMIENTO);
	c3 = new CriterioDisponibilidad(LocalDate.now(),LocalDate.of(2026, 12, 30),15);
	c4 = new CriterioPrecio(5000.0);
	
	List<CriterioBusqueda> listaCA = new ArrayList<>(); listaCA.add(c2); listaCA.add(c3);
	List<CriterioBusqueda> listaCO = new ArrayList<>(); listaCO.add(c1); listaCO.add(c4);
	
	cA = new CriterioAND(listaCA);
	cO = new CriterioOR(listaCO);
	cN = new CriterioNOT(c2);
	setCat1 = new HashSet<>(); setCat1.add(CategoriaServicio.ALOJAMIENTO);
	setCat2 = new HashSet<>(); setCat2.add(CategoriaServicio.EXCURSION);
	setCat3 = new HashSet<>(); setCat3.add(CategoriaServicio.ALQUILERDEAUTO);
	
	
	// Stubs
	when(e1.getNombre()).thenReturn("Servicio Con Nombre Generico"); when(e1.getCategoria()).thenReturn(setCat2);
	when(e1.getPrecioBase()).thenReturn(3500.0); 
	when(e1.hayDisponibilidad(LocalDate.now(),LocalDate.of(2026, 12, 30),15)).thenReturn(false);
	
	when(e2.getNombre()).thenReturn("Otro servicio Con nomBre GeneRicO"); when(e2.getCategoria()).thenReturn(setCat1);
	when(e2.getPrecioBase()).thenReturn(5400.0); 
	when(e2.hayDisponibilidad(LocalDate.now(),LocalDate.of(2026, 12, 30),15)).thenReturn(true);
	
	when(e3.getNombre()).thenReturn("Servicio Cualquiera"); 
	when(e3.getCategoria()).thenReturn(setCat1);
	when(e3.getPrecioBase()).thenReturn(2300.0); 
	when(e3.hayDisponibilidad(LocalDate.now(),LocalDate.of(2026, 12, 30),15)).thenReturn(false);
	
	when(e4.getNombre()).thenReturn("Servicio Con algun nombre"); when(e4.getCategoria()).thenReturn(setCat3);
	when(e4.getPrecioBase()).thenReturn(6780.0); 
	when(e4.hayDisponibilidad(LocalDate.now(),LocalDate.of(2026, 12, 30),15)).thenReturn(true);
  }
	
  @Test
  void testVerificarCriteriosSimples() {
	// Exercise
    List<EntidadCatalogo> filt1 = catTest.buscar(c1);
    List<EntidadCatalogo> filt2 = catTest.buscar(c2);
    List<EntidadCatalogo> filt3 = catTest.buscar(c3);
    List<EntidadCatalogo> filt4 = catTest.buscar(c4);
    
    // Verify
    Assertions.assertTrue(filt1.contains(e1)); Assertions.assertTrue(filt1.contains(e2)); 
    Assertions.assertTrue(filt2.contains(e2)); Assertions.assertTrue(filt2.contains(e3)); 
    Assertions.assertTrue(filt3.contains(e2)); Assertions.assertTrue(filt3.contains(e4)); 
    Assertions.assertTrue(filt4.contains(e1)); Assertions.assertTrue(filt4.contains(e3)); 
  }
  
  @Test
  void testVerificarCriteriosComplejos() {
	// Exercise
	List<EntidadCatalogo> filt1 = catTest.buscar(cA);
	List<EntidadCatalogo> filt2 = catTest.buscar(cO);
	List<EntidadCatalogo> filt3 = catTest.buscar(cN);
	    
	// Verify
	Assertions.assertTrue(filt1.contains(e2)); 
	Assertions.assertFalse(filt1.contains(e1)); Assertions.assertFalse(filt1.contains(e3)); Assertions.assertFalse(filt1.contains(e4));
	
	Assertions.assertTrue(filt2.contains(e1)); Assertions.assertTrue(filt2.contains(e2)); Assertions.assertTrue(filt2.contains(e3)); 
	Assertions.assertFalse(filt2.contains(e4));
	
	Assertions.assertTrue(filt3.contains(e1)); Assertions.assertTrue(filt3.contains(e4)); 
	Assertions.assertFalse(filt3.contains(e2)); Assertions.assertFalse(filt3.contains(e3));
  }
  
  @Test
  void testTodosLosServiciosIndividuales() {
	  when(e1.obtenerServiciosIndividuales()).thenReturn(Set.of(e1));
	  when(e2.obtenerServiciosIndividuales()).thenReturn(Set.of(e2));
	  when(e3.obtenerServiciosIndividuales()).thenReturn(Set.of(e3));
	  when(e4.obtenerServiciosIndividuales()).thenReturn(Set.of(e4));
	  
	  Set<ServicioIndividual> serviciosIndividuales = catTest.todosLosServicios();
	  
	  assertTrue(serviciosIndividuales.contains(e1)); assertTrue(serviciosIndividuales.contains(e2));
	  assertTrue(serviciosIndividuales.contains(e3)); assertTrue(serviciosIndividuales.contains(e4));
  }
  
  @Test
  void testServiciosFiltrados() {
	  when(e1.obtenerServiciosIndividuales()).thenReturn(Set.of(e1));
	  when(e2.obtenerServiciosIndividuales()).thenReturn(Set.of(e2));
	  when(e3.obtenerServiciosIndividuales()).thenReturn(Set.of(e3));
	  when(e4.obtenerServiciosIndividuales()).thenReturn(Set.of(e4));
	  
	  assertTrue(catTest.obtenerServiciosPorCategoria(CategoriaServicio.ALOJAMIENTO).contains(e2));
	  assertTrue(catTest.obtenerServiciosPorCategoria(CategoriaServicio.ALOJAMIENTO).contains(e3));
	  assertTrue(catTest.obtenerServiciosPorCategoria(CategoriaServicio.EXCURSION).contains(e1));
	  assertTrue(catTest.obtenerServiciosPorCategoria(CategoriaServicio.ALQUILERDEAUTO).contains(e4));
	  assertTrue(catTest.obtenerServiciosPorCategoria(CategoriaServicio.VUELO).isEmpty());
  }
}