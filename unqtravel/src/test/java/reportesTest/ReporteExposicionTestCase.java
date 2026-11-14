package reportesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import unqtravel.Reportes.ReporteExposicion;
import unqtravel.Reportes.VisitorReporte;
import unqtravel.catalogo.Catalogo;
import unqtravel.catalogo.Proveedor;
import unqtravel.catalogo.ServicioIndividual;

public class ReporteExposicionTestCase {

    private Catalogo catalogo;

    private ServicioIndividual servicio1;
    private ServicioIndividual servicio2;
    private ServicioIndividual servicio3;

    private Proveedor proveedor1;
    private Proveedor proveedor2;

    private VisitorReporte visitante;

    private ReporteExposicion reporte;

    @BeforeEach
    public void setUp() {
        catalogo = mock(Catalogo.class);

        servicio1 = mock(ServicioIndividual.class);
        servicio2 = mock(ServicioIndividual.class);
        servicio3 = mock(ServicioIndividual.class);

        proveedor1 = mock(Proveedor.class);
        proveedor2 = mock(Proveedor.class);

        visitante = mock(VisitorReporte.class);

        when(servicio1.getOperador()).thenReturn(proveedor1);
        when(servicio2.getOperador()).thenReturn(proveedor1);
        when(servicio3.getOperador()).thenReturn(proveedor2);
    }
    
    @Test
    public void testContarServiciosPorProveedor() {
        when(catalogo.todosLosServicios()).thenReturn(Set.of(servicio1, servicio2, servicio3));

        reporte = new ReporteExposicion(catalogo);

        assertEquals(2, reporte.getCantidadPorProveedor().get(proveedor1));
        assertEquals(1, reporte.getCantidadPorProveedor().get(proveedor2));
    }
    
    @Test
    public void testCantidadTotalServicios() {
        when(catalogo.todosLosServicios()).thenReturn(Set.of(servicio1, servicio2, servicio3));

        reporte = new ReporteExposicion(catalogo);

        assertEquals(3, reporte.getCantidadTotalServicios());
    }
    
    @Test
    public void testCalcularPorcentajePorProveedor() {
        when(catalogo.todosLosServicios()).thenReturn(Set.of(servicio1, servicio2, servicio3));

        reporte = new ReporteExposicion(catalogo);

        assertEquals(66.666, reporte.calcularPorcentaje(proveedor1), 0.01);
        assertEquals(33.333, reporte.calcularPorcentaje(proveedor2), 0.01);
    }
    
    @Test
    public void testPorcentajeProveedorSinServicios() {
        Proveedor proveedorSinServicios = mock(Proveedor.class);

        when(catalogo.todosLosServicios()).thenReturn(Set.of(servicio1, servicio2, servicio3));

        reporte = new ReporteExposicion(catalogo);

        assertEquals(0, reporte.calcularPorcentaje(proveedorSinServicios));
    }
    
    @Test
    public void testReporteConCatalogoVacio() {
        when(catalogo.todosLosServicios()).thenReturn(Set.of());

        reporte = new ReporteExposicion(catalogo);

        assertEquals(0, reporte.getCantidadTotalServicios());
        assertTrue(reporte.getCantidadPorProveedor().isEmpty());
        assertEquals(0, reporte.calcularPorcentaje(proveedor1));
    }
    
    @Test
    public void testAceptarVisitor() {
        when(catalogo.todosLosServicios()).thenReturn(Set.of());

        reporte = new ReporteExposicion(catalogo);

        when(visitante.visitar(reporte)).thenReturn("Reporte generado");

        assertEquals("Reporte generado", reporte.aceptar(visitante));

        verify(visitante).visitar(reporte);
    }
}