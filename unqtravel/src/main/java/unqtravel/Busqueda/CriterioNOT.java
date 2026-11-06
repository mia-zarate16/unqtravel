package unqtravel.Busqueda;

import unqtravel.catalogo.EntidadCatalogo;

public class CriterioNOT implements CriterioBusqueda {

	private CriterioBusqueda criterio;
	
	public CriterioNOT(CriterioBusqueda criterio) {
	  this.criterio = criterio;
	}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return !criterio.cumple(entidad);
	}

}
