package unqtravel.Busqueda;

import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.EntidadCatalogo;

public class CriterioOR implements CriterioBusqueda {

	private List<CriterioBusqueda> criterios = new ArrayList<>();
	
	public CriterioOR(List<CriterioBusqueda> criterios) {
		  this.criterios = criterios;
		}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return criterios.stream()
				        .anyMatch(c -> c.cumple(entidad));
	}

}
