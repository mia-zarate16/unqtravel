package unqtravel.catalogo;

import java.util.ArrayList;
import java.util.List;
import unqtravel.Busqueda.*;

public class Catalogo {
	private List<EntidadCatalogo> items = new ArrayList<>();
    
    public void agregarAlCatalogo(EntidadCatalogo entidad) {
      items.add(entidad);
    }

    public List<EntidadCatalogo> buscar(CriterioBusqueda criterio) {
      return items.stream()
                  .filter(item -> criterio.cumple(item))
                  .toList();
    }
}
