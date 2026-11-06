package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public class CriterioNombre implements CriterioBusqueda {

	private String nombre;
	
	public CriterioNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.getNombre()
		              .toLowerCase()
		              .contains(nombre.toLowerCase());
	}

}
