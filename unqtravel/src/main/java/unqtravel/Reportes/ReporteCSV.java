package unqtravel.Reportes;

import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visitar(ReporteExposicion reporte) {
		// TODO Auto-generated method stub
		return null;
	}

}
