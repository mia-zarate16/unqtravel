package unqtravel.catalogo;

import java.util.Set;

public interface EntidadCatalogo {
	public String getNombre();
	public String getDescripcion();
	public double getPrecioBase();
	public double precioFinal();
	public double getDescuento();
	Set<CategoriaServicio> getCategoria();
	public boolean esValido();
	
}
