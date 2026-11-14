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

public class ReporteTextoTest {

    private ReporteTexto reporteTexto;

    @BeforeEach
    public void setUp() {
        reporteTexto = new ReporteTexto();
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

        String resultado = reporteTexto.visitar(reporte);

        assertTrue(resultado.contains("REPORTE DE SERVICIOS MÁS RESERVADOS"));
        assertTrue(resultado.contains("Hotel Central"));
        assertTrue(resultado.contains("Cantidad de reservas: 3"));
        assertTrue(resultado.contains("Precio final: $15000.0"));
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

        when(reporte.getOcupacionesAlojamiento())
            .thenReturn(List.of(ocupacion));
        when(reporte.getOcupacionesVuelo()).thenReturn(List.of());
        when(reporte.getOcupacionesExcursion()).thenReturn(List.of());
        when(reporte.getOcupacionesAlquilerDeAuto()).thenReturn(List.of());

        when(ocupacion.getServicio()).thenReturn(servicio);
        when(servicio.getNombre()).thenReturn("Hotel Central");
        when(ocupacion.getFecha()).thenReturn(fecha);
        when(ocupacion.getCuposTotales()).thenReturn(20);
        when(ocupacion.getCuposReservados()).thenReturn(12);
        when(ocupacion.getCuposLibres()).thenReturn(8);

        String resultado = reporteTexto.visitar(reporte);

        assertTrue(resultado.contains("REPORTE DE OCUPACIÓN Y CUPO DISPONIBLE"));
        assertTrue(resultado.contains("ALOJAMIENTOS"));
        assertTrue(resultado.contains("Hotel Central"));
        assertTrue(resultado.contains("Cupos totales: 20"));
        assertTrue(resultado.contains("Cupos reservados: 12"));
        assertTrue(resultado.contains("Cupos libres: 8"));
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

        String resultado = reporteTexto.visitar(reporte);

        assertTrue(resultado.contains("REPORTE DE EXPOSICIÓN POR PROVEEDOR / OPERADOR"));
        assertTrue(resultado.contains("Cantidad total de servicios: 4"));
        assertTrue(resultado.contains("Proveedor: Proveedor A"));
        assertTrue(resultado.contains("Servicios asociados: 2"));
        assertTrue(resultado.contains("Exposición: " + String.format("%.2f", 50.0) + "%"));
    }
}