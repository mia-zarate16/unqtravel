package unqtravel.Busqueda;

import java.time.LocalDate;

import unqtravel.catalogo.*;

public class CriterioDisponibilidad implements CriterioBusqueda {
	LocalDate fechaInicio;
	LocalDate fechaFin;
	int cantidad;
	
	public CriterioDisponibilidad(LocalDate fechaInicio,LocalDate fechaFin, int cantidad) {
	  this.fechaInicio = fechaInicio;
	  this.fechaFin = fechaFin;
	  this.cantidad = cantidad;
	}
	
	@Override
	public boolean cumple(EntidadCatalogo entidad) {
		return entidad.hayDisponibilidad(fechaInicio,fechaFin,cantidad);
	}

}
