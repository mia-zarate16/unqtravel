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

public class ReporteCSVTest {

    private ReporteCSV reporteCSV;

    @BeforeEach
    public void setUp() {
        reporteCSV = new ReporteCSV();
    }

    @Test
    public void testVisitarServiciosMasReservados() {
        ReporteServiciosMasReservados reporte = mock(ReporteServiciosMasReservados.class);

        EntidadCatalogo servicio = mock(EntidadCatalogo.class);

        when(servicio.getNombre()).thenReturn("Hotel Central");
        when(servicio.precioFinal()).thenReturn(15000.0);

        Map<EntidadCatalogo, Integer> cantidades = new LinkedHashMap<>();
        cantidades.put(servicio, 3);

        when(reporte.getOrdenados()).thenReturn(cantidades);

        String resultado = reporteCSV.visitar(reporte);

        assertTrue(resultado.contains(
            "Servicio/Paquete,Cantidad de reservas,Precio final"
        ));
        assertTrue(resultado.contains("Hotel Central,3,15000.0"));
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

        String resultado = reporteCSV.visitar(reporte);

        assertTrue(resultado.contains("Categoria,Servicio,Fecha,Cupos totales,Cupos reservados,Cupos libres"));
        assertTrue(resultado.contains("Alojamiento,Hotel Central," + fecha + ",20,12,8"));
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

        String resultado = reporteCSV.visitar(reporte);

        assertTrue(resultado.contains("Cantidad total de servicios,4"));
        assertTrue(resultado.contains("Proveedor,Servicios asociados,Exposicion (%)"));
        assertTrue(resultado.contains("Proveedor A,2," + String.format("%.2f", 50.0)));
    }
}