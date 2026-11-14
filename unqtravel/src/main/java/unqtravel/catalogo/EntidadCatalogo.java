package unqtravel.catalogo;

import java.time.LocalDate;
import java.util.Set;

public interface EntidadCatalogo {
	public String getNombre();
	public String getDescripcion();
	public double getPrecioBase();
	public double precioFinal();
	public double getDescuento();
	Set<CategoriaServicio> getCategoria();
	public boolean esValido();
	public Set<ServicioIndividual> obtenerServiciosIndividuales();
	boolean hayDisponibilidad(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	boolean ocuparCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	void liberarCupos(LocalDate fechaInicio, LocalDate fechaFin, int cantidad);
	
}
