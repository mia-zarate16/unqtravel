package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public class CriterioDisponibilidad implements CriterioBusqueda {

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.hayDisponibilidad();
	}

}
