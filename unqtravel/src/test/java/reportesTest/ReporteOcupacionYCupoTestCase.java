package reportesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import unqtravel.Reportes.*;
import unqtravel.catalogo.*;

public class ReporteOcupacionYCupoTestCase {

    private ReporteOcupacionYCupo reporte;

    private ServicioIndividual alojamiento;
    private ServicioIndividual vuelo;
    private ServicioIndividual excursion;
    private ServicioIndividual alquilerAuto;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @BeforeEach
    public void setUp() {
    	fechaInicio = LocalDate.of(2026, 11, 10);
        fechaFin = LocalDate.of(2026, 11, 11);

        alojamiento = mock(ServicioIndividual.class);
        vuelo = mock(ServicioIndividual.class);
        excursion = mock(ServicioIndividual.class);
        alquilerAuto = mock(ServicioIndividual.class);

        configurarServicio(alojamiento, CategoriaServicio.ALOJAMIENTO, 20);
        configurarServicio(vuelo, CategoriaServicio.VUELO, 100);
        configurarServicio(excursion, CategoriaServicio.EXCURSION, 15);
        configurarServicio(alquilerAuto, CategoriaServicio.ALQUILERDEAUTO, 10);

        List<ServicioIndividual> servicios = List.of(alojamiento, vuelo, excursion, alquilerAuto);

        reporte = new ReporteOcupacionYCupo(fechaInicio, fechaFin, servicios);
    }

    private void configurarServicio(ServicioIndividual servicio,CategoriaServicio categoria,int capacidad) {
        when(servicio.getCategoria()).thenReturn(Set.of(categoria));
        when(servicio.getCapacidadMaxima()).thenReturn(capacidad);
        when(servicio.cuposOcupados(any(LocalDate.class))).thenReturn(0);
    }
    
    @Test
    public void testServiciosDistribuidosPorCategoria() {
        assertEquals(2, reporte.getOcupacionesAlojamiento().size());
        assertEquals(2, reporte.getOcupacionesVuelo().size());
        assertEquals(2, reporte.getOcupacionesExcursion().size());
        assertEquals(2, reporte.getOcupacionesAlquilerDeAuto().size());

        assertSame(alojamiento,reporte.getOcupacionesAlojamiento().get(0).getServicio());

        assertSame(vuelo,reporte.getOcupacionesVuelo().get(0).getServicio());

        assertSame(excursion,reporte.getOcupacionesExcursion().get(0).getServicio());

        assertSame(alquilerAuto,reporte.getOcupacionesAlquilerDeAuto().get(0).getServicio());
    }
    
    @Test
    public void testCalcularCuposCorrectamente() {
        when(alojamiento.cuposOcupados(fechaInicio)).thenReturn(12);

        reporte = new ReporteOcupacionYCupo(fechaInicio,fechaFin,List.of(alojamiento));

            OcupacionServicio ocupacion = reporte.getOcupacionesAlojamiento().get(0);

            assertEquals(20, ocupacion.getCuposTotales());
            assertEquals(12, ocupacion.getCuposReservados());
            assertEquals(8, ocupacion.getCuposLibres());
    }
    
    @Test
    public void testGenerarOcupacionesParaCadaFechaDelPeriodo() {
        List<OcupacionServicio> ocupaciones = reporte.getOcupacionesAlojamiento();

        assertEquals(fechaInicio, ocupaciones.get(0).getFecha());
        assertEquals(fechaFin, ocupaciones.get(1).getFecha());
    }
    
    @Test
    public void testPeriodoDeUnSoloDia() {
        ReporteOcupacionYCupo reporteUnDia = new ReporteOcupacionYCupo(fechaInicio,fechaInicio,List.of(alojamiento));

        assertEquals(1, reporteUnDia.getOcupacionesAlojamiento().size());
    }
    
    @Test
    public void testReporteSinServicios() {
        ReporteOcupacionYCupo reporteVacio = new ReporteOcupacionYCupo(fechaInicio,fechaFin,List.of());

        assertTrue(reporteVacio.getOcupacionesAlojamiento().isEmpty());
        assertTrue(reporteVacio.getOcupacionesVuelo().isEmpty());
        assertTrue(reporteVacio.getOcupacionesExcursion().isEmpty());
        assertTrue(reporteVacio.getOcupacionesAlquilerDeAuto().isEmpty());
    }
    
    @Test
    public void testAceptarVisitor() {
        VisitorReporte visitor = mock(VisitorReporte.class);

        when(visitor.visitar(reporte)).thenReturn("Reporte generado");

        String resultado = reporte.aceptar(visitor);

        assertEquals("Reporte generado", resultado);
        verify(visitor).visitar(reporte);
    }
}