package unqtravel.catalogo;

import java.time.LocalDate;

public interface EntidadCatalogo {
	
	public String getNombre();
	public String getDescripcion();
	public double precioFinal();
	public double getDescuento();
	public boolean esValido();
	boolean hayDisponibilidad(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	boolean ocuparCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	void liberarCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	
}
