package unqtravel.Busqueda;

import unqtravel.catalogo.*;

public interface CriterioBusqueda {
  abstract public boolean cumple(Catalogo catalogo);
}
