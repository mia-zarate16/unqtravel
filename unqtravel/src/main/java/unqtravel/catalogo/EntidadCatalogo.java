package unqtravel.catalogo;

import java.util.Set;

public interface EntidadCatalogo {
	
	public String getNombre();
	public String getDescripcion();
	public double getPrecioBase();
	Set<CategoriaServicio> getCategoria();
	public double precioFinal();
	public double getDescuento();
	public boolean esValido();
	
}
