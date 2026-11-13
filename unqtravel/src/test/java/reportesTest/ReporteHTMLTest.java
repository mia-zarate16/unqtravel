package reportesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;

import unqtravel.Reportes.*;
import unqtravel.catalogo.*;

public class ReporteHTMLTest {

    private ReporteHTML reporteHTML;

    @BeforeEach
    public void setUp() { reporteHTML = new ReporteHTML(); }

    @Test
    public void testVisitarServiciosMasReservados() {
        ReporteServiciosMasReservados reporte = mock(ReporteServiciosMasReservados.class);

        EntidadCatalogo servicio = mock(EntidadCatalogo.class);

        when(servicio.getNombre()).thenReturn("Hotel Central");
        when(servicio.precioFinal()).thenReturn(15000.0);

        Map<EntidadCatalogo, Integer> cantidades = new LinkedHashMap<>();
        cantidades.put(servicio, 3);

        when(reporte.getOrdenados()).thenReturn(cantidades);

        String resultado = reporteHTML.visitar(reporte);

        assertTrue(resultado.contains("<html>"));
        assertTrue(resultado.contains("<h1>Servicios más reservados</h1>"));
        assertTrue(resultado.contains("<th>Servicio/Paquete</th>"));
        assertTrue(resultado.contains("<td>Hotel Central</td>"));
        assertTrue(resultado.contains("<td>3</td>"));
        assertTrue(resultado.contains("<td>15000.0</td>"));
        assertTrue(resultado.contains("</html>"));
    }

    @Test
    public void testVisitarOcupacionYCupo() {
        ReporteOcupacionYCupo reporte =
            mock(ReporteOcupacionYCupo.class);

        OcupacionServicio ocupacion = mock(OcupacionServicio.class);
        ServicioIndividual servicio = mock(ServicioIndividual.class);

        LocalDate fecha = LocalDate.of(2026, 11, 13);

        when(reporte.getFechaInicio()).thenReturn(fecha);
        when(reporte.getFechaFin()).thenReturn(fecha);

        when(reporte.getOcupacionesAlojamiento()).thenReturn(List.of(ocupacion));
        when(reporte.getOcupacionesVuelo()).thenReturn(List.of());
        when(reporte.getOcupacionesExcursion()).thenReturn(List.of());
        when(reporte.getOcupacionesAlquilerDeAuto()).thenReturn(List.of());

        when(ocupacion.getServicio()).thenReturn(servicio);
        when(servicio.getNombre()).thenReturn("Hotel Central");
        when(ocupacion.getFecha()).thenReturn(fecha);
        when(ocupacion.getCuposTotales()).thenReturn(20);
        when(ocupacion.getCuposReservados()).thenReturn(12);
        when(ocupacion.getCuposLibres()).thenReturn(8);

        String resultado = reporteHTML.visitar(reporte);

        assertTrue(resultado.contains("<html>"));
        assertTrue(resultado.contains("<h1>Reporte de Ocupación y Cupo Disponible</h1>"));
        assertTrue(resultado.contains("<h2>Alojamientos</h2>"));
        assertTrue(resultado.contains("<td>Hotel Central</td>"));
        assertTrue(resultado.contains("<td>20</td>"));
        assertTrue(resultado.contains("<td>12</td>"));
        assertTrue(resultado.contains("<td>8</td>"));
        assertTrue(resultado.contains("</html>"));
    }

    @Test
    public void testVisitarExposicion() {
        ReporteExposicion reporte = mock(ReporteExposicion.class);
        Proveedor proveedor = mock(Proveedor.class);

        Map<Proveedor, Integer> cantidades = new HashMap<>();
        cantidades.put(proveedor, 2);

        when(reporte.getCantidadTotalServicios()).thenReturn(4);
        when(reporte.getCantidadPorProveedor()).thenReturn(cantidades);
        when(proveedor.getNombre()).thenReturn("Proveedor A");

        String resultado = reporteHTML.visitar(reporte);

        assertTrue(resultado.contains("<html>"));
        assertTrue(resultado.contains(
            "<h1>Reporte de Exposición por Proveedor / Operador</h1>"
        ));
        assertTrue(resultado.contains("<td>Proveedor A</td>"));
        assertTrue(resultado.contains("<td>2</td>"));
        assertTrue(resultado.contains(
            "<td>" + String.format("%.2f", 50.0) + "%</td>"
        ));
        assertTrue(resultado.contains("</html>"));
    }
}