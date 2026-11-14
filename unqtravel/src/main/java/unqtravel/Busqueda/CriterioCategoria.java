package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public class CriterioCategoria implements CriterioBusqueda {

	private CategoriaServicio categoria;
	
	public CriterioCategoria(CategoriaServicio categoria) {
	  this.categoria = categoria;
	}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.getCategoria().contains(this.categoria);
	}

}
