package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public class CriterioCategoria implements CriterioBusqueda {

	private String categoria;
	
	public CriterioCategoria(String categoria) {
	  this.categoria = categoria;
	}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.getCategoria() == this.categoria;
	}

}
