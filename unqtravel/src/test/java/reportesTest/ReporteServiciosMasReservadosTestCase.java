package reportesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.reservaYSusEstados.Reserva;
import unqtravel.Reportes.ReporteServiciosMasReservados;

public class ReporteServiciosMasReservadosTestCase {

    private ReporteServiciosMasReservados reporte;

    private Reserva reserva1;
    private Reserva reserva2;
    private Reserva reserva3;

    private EntidadCatalogo servicio1;
    private EntidadCatalogo servicio2;
    private EntidadCatalogo servicio3;

    @BeforeEach
    public void setUp() {
        reserva1 = mock(Reserva.class);
        reserva2 = mock(Reserva.class);
        reserva3 = mock(Reserva.class);

        servicio1 = mock(EntidadCatalogo.class);
        servicio2 = mock(EntidadCatalogo.class);
        servicio3 = mock(EntidadCatalogo.class);
    }

    @Test
    public void testContarReservasPorItem() {
        when(reserva1.getItems()).thenReturn(List.of(servicio1, servicio2));
        when(reserva2.getItems()).thenReturn(List.of(servicio1, servicio3));
        when(reserva3.getItems()).thenReturn(List.of(servicio1, servicio2));

        reporte = new ReporteServiciosMasReservados(List.of(reserva1, reserva2, reserva3));

        Map<EntidadCatalogo, Integer> resultado = reporte.getOrdenados();

        assertEquals(3, resultado.get(servicio1));
        assertEquals(2, resultado.get(servicio2));
        assertEquals(1, resultado.get(servicio3));
    }

    @Test
    public void testOrdenarItemsPorCantidad() {
        when(reserva1.getItems()).thenReturn(List.of(servicio2, servicio1));
        when(reserva2.getItems()).thenReturn(List.of(servicio1, servicio3));
        when(reserva3.getItems()).thenReturn(List.of(servicio1, servicio2));

        reporte = new ReporteServiciosMasReservados(List.of(reserva1, reserva2, reserva3));

        Map<EntidadCatalogo, Integer> resultado = reporte.getOrdenados();

        List<EntidadCatalogo> itemsOrdenados = new ArrayList<>(resultado.keySet());

        assertEquals(servicio1, itemsOrdenados.get(0));
        assertEquals(servicio2, itemsOrdenados.get(1));
        assertEquals(servicio3, itemsOrdenados.get(2));
    }

    @Test
    public void testReservaConVariosItems() {
        when(reserva1.getItems()).thenReturn(List.of(servicio1, servicio2, servicio3));

        reporte = new ReporteServiciosMasReservados(List.of(reserva1));
        
        Map<EntidadCatalogo, Integer> resultado = reporte.getOrdenados();

        assertEquals(1, resultado.get(servicio1));
        assertEquals(1, resultado.get(servicio2));
        assertEquals(1, resultado.get(servicio3));
    }

    @Test
    public void testReporteSinReservas() {
        reporte = new ReporteServiciosMasReservados(List.of());

        assertTrue(reporte.getOrdenados().isEmpty());
    }

    @Test
    public void testReporteConReservasSinItems() {
        when(reserva1.getItems()).thenReturn(List.of());
        when(reserva2.getItems()).thenReturn(List.of());

        reporte = new ReporteServiciosMasReservados(List.of(reserva1, reserva2));

        assertTrue(reporte.getOrdenados().isEmpty());
    }
}