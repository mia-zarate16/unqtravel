package unqtravel.Reportes;

import java.util.List;
import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.catalogo.Proveedor;

public class ReporteCSV implements VisitorReporte {

	@Override
	public String visitar(ReporteServiciosMasReservados reporte) {
	  StringBuilder csv = new StringBuilder();
      csv.append("Servicio/Paquete,Cantidad de reservas,Precio final\n");
      
      Map<EntidadCatalogo, Integer> cantidades = reporte.getOrdenados();

      for (Map.Entry<EntidadCatalogo, Integer> entrada : cantidades.entrySet()) {
        EntidadCatalogo item = entrada.getKey();
        Integer cantidad = entrada.getValue();
        csv.append(item.getNombre()).append(",");
        csv.append(cantidad).append(",");
        csv.append(item.precioFinal()).append("\n");
      }

      return csv.toString();
	}

	@Override
	public String visitar(ReporteOcupacionYCupo reporte) {
	    StringBuilder csv = new StringBuilder();

	    csv.append("REPORTE DE OCUPACION Y CUPO DISPONIBLE\n");
	    csv.append("Periodo,")
	       .append(reporte.getFechaInicio())
	       .append(" - ")
	       .append(reporte.getFechaFin())
	       .append("\n\n");
	    
	    csv.append("Categoria,Servicio,Fecha,Cupos totales,Cupos reservados,Cupos libres\n");

	    agregarOcupaciones(csv, "Alojamiento",reporte.getOcupacionesAlojamiento());
	    agregarOcupaciones(csv, "Vuelo",reporte.getOcupacionesVuelo());
	    agregarOcupaciones(csv, "Excursion",reporte.getOcupacionesExcursion());
	    agregarOcupaciones(csv, "Alquiler de auto",reporte.getOcupacionesAlquilerDeAuto());

	    return csv.toString();
	}

	@Override
	public String visitar(ReporteExposicion reporte) {
	    StringBuilder csv = new StringBuilder();

	    csv.append("REPORTE DE EXPOSICION POR PROVEEDOR / OPERADOR\n");

	    csv.append("Cantidad total de servicios,")
	       .append(reporte.getCantidadTotalServicios())
	       .append("\n\n");

	    csv.append("Proveedor,Servicios asociados,Exposicion (%)\n");

	    agregarExposicion(csv,reporte.getCantidadPorProveedor(),reporte.getCantidadTotalServicios());

	    return csv.toString();
	}
	
	private void agregarExposicion(StringBuilder csv,Map<Proveedor, Integer> cantidadPorProveedor,int cantidadTotalServicios) {

	    for (Map.Entry<Proveedor, Integer> entrada : cantidadPorProveedor.entrySet()) {

	        Proveedor proveedor = entrada.getKey();
	        int cantidad = entrada.getValue();

	        double porcentaje = cantidadTotalServicios == 0 ? 0 : (double) cantidad / cantidadTotalServicios * 100;

	        csv.append(proveedor.getNombre()).append(",")
	           .append(cantidad).append(",")
	           .append(String.format("%.2f", porcentaje))
	           .append("\n");
	    }
	}
	
	private void agregarOcupaciones(StringBuilder csv,String categoria,List<OcupacionServicio> ocupaciones) {
	    for (OcupacionServicio ocupacion : ocupaciones) {
	        csv.append(categoria).append(",")
	           .append(ocupacion.getServicio().getNombre()).append(",")
	           .append(ocupacion.getFecha()).append(",")
	           .append(ocupacion.getCuposTotales()).append(",")
	           .append(ocupacion.getCuposReservados()).append(",")
	           .append(ocupacion.getCuposLibres())
	           .append("\n");
	    }

	    csv.append("\n");
	}

}
