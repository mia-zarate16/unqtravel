package unqtravel.Reportes;

import java.util.List;
import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;

public class ReporteHTML implements VisitorReporte {

	@Override
	public String visitar(ReporteServiciosMasReservados reporte) {
	  StringBuilder html = new StringBuilder();
		
	  html.append("<html><body>");
	  html.append("<title>Servicios más reservados</title>");
	  html.append("</head>");
	  html.append("<body>");

      html.append("<h1>Servicios más reservados</h1>");
      html.append("<table border='1'>");

      html.append("<tr>");
      html.append("<th>Servicio/Paquete</th>");
      html.append("<th>Cantidad de reservas</th>");
      html.append("<th>Precio final</th>");
      html.append("</tr>");
        
      Map<EntidadCatalogo, Integer> cantidades = reporte.getOrdenados();

      for (Map.Entry<EntidadCatalogo, Integer> entrada : cantidades.entrySet()) {
        EntidadCatalogo item = entrada.getKey();
        Integer cantidad = entrada.getValue();
        html.append("<tr>");
        html.append("<td>").append(item.getNombre()).append("</td>");
        html.append("<td>").append(cantidad).append("</td>");
        html.append("<td>").append(item.precioFinal()).append("</td>");
        html.append("</tr>");
      }

      html.append("</table>");
      html.append("</body>");
      html.append("</html>");

      return html.toString();
	}

	@Override
	public String visitar(ReporteOcupacionYCupo reporte) {
	    StringBuilder html = new StringBuilder();

	    html.append("<html><head>");
	    html.append("<title>Reporte de Ocupación y Cupo Disponible</title>");
	    html.append("</head>");

	    html.append("<body>");
	    html.append("<h1>Reporte de Ocupación y Cupo Disponible</h1>");

	    html.append("<p>Período: ")
	        .append(reporte.getFechaInicio())
	        .append(" - ")
	        .append(reporte.getFechaFin())
	        .append("</p>");

	    agregarOcupaciones(html, "Alojamientos",
	                       reporte.getOcupacionesAlojamiento());

	    agregarOcupaciones(html, "Vuelos",
	                       reporte.getOcupacionesVuelo());

	    agregarOcupaciones(html, "Excursiones",
	                       reporte.getOcupacionesExcursion());

	    agregarOcupaciones(html, "Alquiler de autos",
	                       reporte.getOcupacionesAlquilerDeAuto());

	    html.append("</body>");
	    html.append("</html>");

	    return html.toString();
	}

	@Override
	public String visitar(ReporteExposicion reporte) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void agregarOcupaciones(
	        StringBuilder html,
	        String categoria,
	        List<OcupacionServicio> ocupaciones) {

	    html.append("<h2>").append(categoria).append("</h2>");

	    html.append("<table border='1'>");

	    html.append("<tr>");
	    html.append("<th>Servicio</th>");
	    html.append("<th>Fecha</th>");
	    html.append("<th>Cupos totales</th>");
	    html.append("<th>Cupos reservados</th>");
	    html.append("<th>Cupos libres</th>");
	    html.append("</tr>");

	    for (OcupacionServicio ocupacion : ocupaciones) {
	        html.append("<tr>");

	        html.append("<td>")
	            .append(ocupacion.getServicio().getNombre())
	            .append("</td>");

	        html.append("<td>")
	            .append(ocupacion.getFecha())
	            .append("</td>");

	        html.append("<td>")
	            .append(ocupacion.getCuposTotales())
	            .append("</td>");

	        html.append("<td>")
	            .append(ocupacion.getCuposReservados())
	            .append("</td>");

	        html.append("<td>")
	            .append(ocupacion.getCuposLibres())
	            .append("</td>");

	        html.append("</tr>");
	    }

	    html.append("</table>");
	}

}
