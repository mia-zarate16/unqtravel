package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public class CriterioPrecio implements CriterioBusqueda {

	private Double precio;
	
	public CriterioPrecio(Double precio) {
		this.precio = precio;
	}
	
	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.getPrecioBase() == this.precio;
	}

}
