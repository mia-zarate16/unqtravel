package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProveedorTest {
	
	private Proveedor operador;
	
	@BeforeEach
	public void setUp() {
		operador = new Proveedor("Nahiara", "email:unqNahiara@hotmail.com");
	}
	
	@Test
	public void testGetNombre() {
		//EXERCISE
		String resultado = operador.getNombre();
		
		//VERIFY
		assertEquals("Nahiara", resultado);
	}
	
	@Test
	public void testGetDatosContacto() {
		//EXERCISE
		String resultado = operador.getDatosContacto();
		
		//VERIFY
		assertEquals("email:unqNahiara@hotmail.com", resultado);
	}
}
