package unqtravel.Reportes;

import java.util.List;
import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;

public class ReporteTexto implements VisitorReporte {

	@Override
	public String visitar(ReporteServiciosMasReservados reporte) {

	  StringBuilder texto = new StringBuilder();
		
	  texto.append("REPORTE DE SERVICIOS MÁS RESERVADOS\n");
	  texto.append("-----------------------------------\n");

	  Map<EntidadCatalogo, Integer> cantidades = reporte.getOrdenados();
	  for (Map.Entry<EntidadCatalogo, Integer> entrada : cantidades.entrySet()) {
	    EntidadCatalogo item = entrada.getKey();
	    Integer cantidad = entrada.getValue();
	    texto.append("Servicio/Paquete: ")
	         .append(item.getNombre())
	         .append("\n");
	    texto.append("Cantidad de reservas: ")
	         .append(cantidad)
	         .append("\n");
	    texto.append("Precio final: $")
	         .append(item.precioFinal())
	         .append("\n\n");
	  }
	  
	  return texto.toString();
	}

	@Override
	public String visitar(ReporteOcupacionYCupo reporte) {
	    StringBuilder texto = new StringBuilder();

	    texto.append("REPORTE DE OCUPACIÓN Y CUPO DISPONIBLE\n");
	    texto.append("Período: ")
	          .append(reporte.getFechaInicio())
	          .append(" - ")
	          .append(reporte.getFechaFin())
	          .append("\n\n");

	    agregarOcupaciones(texto, "ALOJAMIENTOS",
	                       reporte.getOcupacionesAlojamiento());

	    agregarOcupaciones(texto, "VUELOS",
	                       reporte.getOcupacionesVuelo());

	    agregarOcupaciones(texto, "EXCURSIONES",
	                       reporte.getOcupacionesExcursion());

	    agregarOcupaciones(texto, "ALQUILER DE AUTOS",
	                       reporte.getOcupacionesAlquilerDeAuto());

	    return texto.toString();
	}
	@Override
	public String visitar(ReporteExposicion reporte) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void agregarOcupaciones(StringBuilder texto,String categoria,List<OcupacionServicio> ocupaciones) {
	    texto.append("=== ").append(categoria).append(" ===\n");

	    for (OcupacionServicio ocupacion : ocupaciones) {
	        texto.append("Servicio: ")
	             .append(ocupacion.getServicio().getNombre())
	             .append("\n");

	        texto.append("Fecha: ")
	             .append(ocupacion.getFecha())
	             .append("\n");

	        texto.append("Cupos totales: ")
	             .append(ocupacion.getCuposTotales())
	             .append("\n");

	        texto.append("Cupos reservados: ")
	             .append(ocupacion.getCuposReservados())
	             .append("\n");

	        texto.append("Cupos libres: ")
	             .append(ocupacion.getCuposLibres())
	             .append("\n\n");
	    }
	}

}
