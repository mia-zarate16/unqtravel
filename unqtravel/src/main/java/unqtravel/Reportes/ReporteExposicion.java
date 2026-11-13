package unqtravel.Reportes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import unqtravel.catalogo.Catalogo;
import unqtravel.catalogo.Proveedor;
import unqtravel.catalogo.ServicioIndividual;

public class ReporteExposicion implements Reporte {
	private Map<Proveedor,Integer> cantidadPorProveedor;
	private int cantidadTotalServicios;
	
	public ReporteExposicion(Catalogo catalogo) {
	    this.cantidadPorProveedor = new HashMap<>();

	    Set<ServicioIndividual> servicios =
	        catalogo.todosLosServicios();

	    this.cantidadTotalServicios = servicios.size();

	    calcularExposicion(servicios);
	}
	
	public Map<Proveedor, Integer> getCantidadPorProveedor() {
		return cantidadPorProveedor;
	}

	public int getCantidadTotalServicios() {
		return cantidadTotalServicios;
	}

	private void calcularExposicion(Set<ServicioIndividual> servicios) {
	    for (ServicioIndividual servicio : servicios) {
	        Proveedor proveedor = servicio.getOperador();
	        cantidadPorProveedor.merge(proveedor, 1, Integer::sum);
	    }
	}
	
	public double calcularPorcentaje(Proveedor proveedor) {
	    if (cantidadTotalServicios == 0) {
	        return 0;
	    }

	    int cantidad = cantidadPorProveedor.getOrDefault(
	        proveedor, 0
	    );

	    return (double) cantidad / cantidadTotalServicios * 100;
	}

	@Override
	public String aceptar(VisitorReporte visitante) {
		return visitante.visitar(this);
	}

}
