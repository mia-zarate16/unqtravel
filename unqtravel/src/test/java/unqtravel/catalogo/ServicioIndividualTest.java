package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.List;

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

    
    @BeforeEach
    public void setUp() {
        operador = mock(Proveedor.class);

        servicioHotel = new ServicioIndividual("1", "NocheDeHotelCentral", operador, 120.0, 0.10,
            "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO);
        
        servicioExcursion = new ServicioIndividual("2", "ExcursionGuiadaMuseo", operador, 100.0, 0.10, 
				"Excursion guiada al museo", CategoriaServicio.EXCURSION);
        
        capacidadV1 = new AtributoDinamico("CapacidadMaxima", true, new TipoValorNumero());
        capacidadV2 = new AtributoDinamico("CapacidadMaxima", false, new TipoValorTexto());
        ratingV1 = new AtributoDinamico("RatingEstrellas", false, new TipoValorNumero());
        ratingV2 = new AtributoDinamico("RatingEstrellas", true, new TipoValorNumero());
        politicaMV1 = new AtributoDinamico("PoliticaMascotas", false, new TipoValorBooleano());
    }
	
	
	@Test
	public void testPermiteAgregarAtributo() {
		//SETUP
		servicioHotel.agregarAtributo(capacidadV1);
		
		//EXERCISE 
		boolean resultado = servicioHotel.tieneAtributo("CapacidadMaxima");
		
		//VERIFY
		assertTrue(resultado);
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
		//SETUP
		servicioHotel.agregarAtributo(ratingV2);
		ValorNumero numero = new ValorNumero(25);
		
		// EXERCISE
		servicioHotel.asignarValorAtributo(ratingV2, numero);
		
		//VERIFY
		
		assertSame(numero, servicioHotel.obtenerAtributo("RatingEstrellas").getValor());
		assertTrue(ratingV2.esValido());
	}
	
	@Test
	public void testNoSePuedeAsignarValorAlAtributo() {
		//SETUP
		servicioHotel.agregarAtributo(politicaMV1);
		servicioHotel.agregarAtributo(ratingV2);
		
		//EXERCISE Y VERIFY
		assertThrows(IllegalArgumentException.class, () -> servicioHotel.asignarValorAtributo(capacidadV1, new ValorNumero(9)));
	}
	
	@Test
	public void testServicioConAtributoObligatorioSinValor() {
	    // SETUP
		servicioHotel.agregarAtributo(capacidadV1);

	    // EXERCISE
	    boolean resultado = servicioHotel.sonValidosLosDinamicos();

	    // VERIFY
	    assertFalse(resultado);
	}
	
	@Test
	public void testServicioConAtributosValidos() {
	    // SETUP
		servicioExcursion.agregarAtributo(capacidadV1);
		servicioExcursion.agregarAtributo(politicaMV1);
		servicioExcursion.agregarAtributo(ratingV2);
		
		ValorNumero valor1 = new ValorNumero(8);
		ValorNumero valor2 = new ValorNumero(9);
		
		servicioExcursion.asignarValorAtributo(capacidadV1, valor1);
		servicioExcursion.asignarValorAtributo(ratingV2, valor2);

	    // EXERCISE
	    boolean resultado = servicioExcursion.sonValidosLosDinamicos();

	    // VERIFY
	    assertTrue(resultado);
	}
	
	@Test
	public void testEsInvalidoElServicio() {
		//SETUP
		ServicioIndividual servicio = new ServicioIndividual("123", "    ", operador, 120.0, 0.10,
	            "Excursion guiada al museo", CategoriaServicio.EXCURSION);
		
		servicio.agregarAtributo(ratingV2);
		
		ValorNumero valor2 = new ValorNumero(30);
		
		servicio.asignarValorAtributo(ratingV2, valor2);
		
		//EXERCISE
		boolean resultado = servicio.esValido();
		
		//VERIFY
		assertFalse(resultado);
	}
	
	@Test
	public void testEsValidoElServicio() {
		//SETUP
		servicioExcursion.agregarAtributo(politicaMV1);
		
		//EXERCISE
		boolean resultado = servicioExcursion.esValido();
		
		//VERIFY
		assertTrue(resultado);
	}
	
	@Test
	public void testEsInvalidoElServicioPorAtributoDinamicoInvalido() {
		//SETUP
		servicioHotel.agregarAtributo(capacidadV1);
	    
	    ValorTexto texto = new ValorTexto("nueve");
	    
	    servicioHotel.asignarValorAtributo(capacidadV1, texto);
	    
	    //EXERCISE
	    boolean resultado = servicioHotel.esValido();
	    
	    //VERIFY
	    assertFalse(resultado);
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
	    List<AtributoDinamico> resultado = servicioHotel.getAtributosDinamicos();

	    // VERIFY
	    assertEquals(2, resultado.size());

	    assertTrue(resultado.contains(capacidadV1));
	    assertTrue(resultado.contains(ratingV2));
	}
	
	@Test
	public void testConstructorConProveedorNulo() {
	    // EXERCISE Y VERIFY
	    assertThrows(NullPointerException.class, () -> new ServicioIndividual("3", "NocheDeHotelCentral", null, 
	    		120.0, 0.10, "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO));
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
	        "Noche en un hotel central", CategoriaServicio.ALOJAMIENTO);

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
	    CategoriaServicio resultado = servicioHotel.getCategoria();

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
}
