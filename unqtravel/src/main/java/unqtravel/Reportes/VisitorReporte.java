package unqtravel.Reportes;

public interface VisitorReporte {
	String visitar(ReporteServiciosMasReservados reporte);
    String visitar(ReporteOcupacionYCupo reporte);
    String visitar(ReporteExposicion reporte);
}
