package unqtravel.Busqueda;

import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.EntidadCatalogo;

public class CriterioAND implements CriterioBusqueda {

	private List<CriterioBusqueda> criterios = new ArrayList<>();
	
	public CriterioAND(List<CriterioBusqueda> criterios) {
	  this.criterios = criterios;
	}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return criterios.stream()
				        .allMatch(c -> c.cumple(entidad));
	}

}
