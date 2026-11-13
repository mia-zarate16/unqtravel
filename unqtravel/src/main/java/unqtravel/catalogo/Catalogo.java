package unqtravel.catalogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
    public Set<ServicioIndividual> todosLosServicios() {
      Set<ServicioIndividual> servicios = new HashSet<>();

        for (EntidadCatalogo item : items) {
            servicios.addAll(item.obtenerServiciosIndividuales());
        }

        return servicios;
    }
    
    public Set<ServicioIndividual> obtenerServiciosPorCategoria(CategoriaServicio categoria) {
        Set<ServicioIndividual> servicios = new HashSet<>();

        for (ServicioIndividual servicio : todosLosServicios()) {
            if (servicio.getCategoria().contains(categoria)) {
                servicios.add(servicio);
            }
        }

        return servicios;
    }
    
}

