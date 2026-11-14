package unqtravel.Reportes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unqtravel.catalogo.CategoriaServicio;
import unqtravel.catalogo.ServicioIndividual;

public class ReporteOcupacionYCupo implements Reporte {

	private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<ServicioIndividual> servicios;
    private List<OcupacionServicio> ocupacionesAlojamiento;
    private List<OcupacionServicio> ocupacionesVuelo;
    private List<OcupacionServicio> ocupacionesExcursion;
    private List<OcupacionServicio> ocupacionesAlquilerDeAuto;
    
    public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public List<OcupacionServicio> getOcupacionesAlojamiento() {
		return ocupacionesAlojamiento;
	}

	public List<OcupacionServicio> getOcupacionesVuelo() {
		return ocupacionesVuelo;
	}

	public List<OcupacionServicio> getOcupacionesExcursion() {
		return ocupacionesExcursion;
	}
	
	public List<OcupacionServicio> getOcupacionesAlquilerDeAuto() {
		return ocupacionesAlquilerDeAuto;
	}

	public ReporteOcupacionYCupo(LocalDate fechaInicio, LocalDate fechaFin, List<ServicioIndividual> servicios) {
	  this.servicios = servicios;
      this.fechaInicio = fechaInicio;
      this.fechaFin = fechaFin;
      this.ocupacionesAlojamiento = crearOcupaciones(filtrarPorCategoria(CategoriaServicio.ALOJAMIENTO));
      this.ocupacionesVuelo = crearOcupaciones(filtrarPorCategoria(CategoriaServicio.VUELO));
      this.ocupacionesExcursion = crearOcupaciones(filtrarPorCategoria(CategoriaServicio.EXCURSION));
      this.ocupacionesAlquilerDeAuto = crearOcupaciones(filtrarPorCategoria(CategoriaServicio.ALQUILERDEAUTO));
    }
    
    private List<ServicioIndividual> filtrarPorCategoria(CategoriaServicio cat) {
      return servicios.stream()
                      .filter(s -> s.getCategoria().contains(cat))
                      .toList();
    }
    
    private List<OcupacionServicio> crearOcupaciones(List<ServicioIndividual> servicios) {
      List<OcupacionServicio> ocupaciones = new ArrayList<>();

      for (ServicioIndividual servicio : servicios) {
        for (LocalDate fecha = fechaInicio;!fecha.isAfter(fechaFin);fecha = fecha.plusDays(1)) {
          int totales = servicio.getCapacidadMaxima();
          int reservados = servicio.cuposOcupados(fecha);
          int libres = totales - reservados;

          ocupaciones.add(new OcupacionServicio(servicio, fecha, totales, reservados, libres));
        }
      }
      
      return ocupaciones;
    }
    
	@Override
	public String aceptar(VisitorReporte visitante) {
		return visitante.visitar(this);
	}

}
