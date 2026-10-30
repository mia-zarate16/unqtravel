package unqtravel.catalogo;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
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
	
	
  @Test
  void testVerificarMockito() {
    
  }
}