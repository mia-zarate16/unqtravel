package unqtravel.catalogo;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import unqtravel.Busqueda.*;
import unqtravel.catalogo.*;

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
  private EntidadCatalogo e1;
  private EntidadCatalogo e2;
  private EntidadCatalogo e3;
  private EntidadCatalogo e4;
	
  @BeforeEach
  void setUp() {
	//DOC
	e1 = mock(EntidadCatalogo.class);
	e2 = mock(EntidadCatalogo.class);
	e3 = mock(EntidadCatalogo.class);
	e4 = mock(EntidadCatalogo.class);
	  
	//SUT
	catTest = new Catalogo();
	catTest.agregarAlCatalogo(e1); catTest.agregarAlCatalogo(e2);
	catTest.agregarAlCatalogo(e3); catTest.agregarAlCatalogo(e4); 
	
	c1 = new CriterioNombre("Nombre Generico");
	c2 = new CriterioCategoria("ALOJAMIENTO");
	c3 = new CriterioDisponibilidad();
	c4 = new CriterioPrecio(5000.0);
	
	List<CriterioBusqueda> listaCA = new ArrayList<>(); listaCA.add(c2); listaCA.add(c3);
	List<CriterioBusqueda> listaCO = new ArrayList<>(); listaCO.add(c1); listaCO.add(c4);
	
	cA = new CriterioAND(listaCA);
	cO = new CriterioOR(listaCO);
	cN = new CriterioNOT(c2);
	
	// Stubs
	when(e1.getNombre()).thenReturn("Servicio Con Nombre Generico"); when(e1.getCategoria()).thenReturn("EXCURSION");
	when(e1.getPrecioBase()).thenReturn(3500.0); when(e1.hayDisponibilidad()).thenReturn(false);
	
	when(e2.getNombre()).thenReturn("Otro servicio Con nomBre GeneRicO"); when(e2.getCategoria()).thenReturn("ALOJAMIENTO");
	when(e2.getPrecioBase()).thenReturn(5400.0); when(e2.hayDisponibilidad()).thenReturn(true);
	
	when(e3.getNombre()).thenReturn("Servicio Cualquiera"); when(e3.getCategoria()).thenReturn("ALOJAMIENTO");
	when(e3.getPrecioBase()).thenReturn(2300.0); when(e3.hayDisponibilidad()).thenReturn(false);
	
	when(e4.getNombre()).thenReturn("Servicio Con algun nombre"); when(e4.getCategoria()).thenReturn("ALQUILERDEAUTO");
	when(e4.getPrecioBase()).thenReturn(6780.0); when(e4.hayDisponibilidad()).thenReturn(true);
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
}