package unqtravel.catalogo;

public interface EntidadCatalogo {
  String getNombre();
  double getPrecioBase();
  double getPrecioFinal();
  String getCategoria();
  boolean hayDisponibilidad();
}
