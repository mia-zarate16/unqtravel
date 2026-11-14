package reserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.metodoDeTrasladoYTasas.MetodoTraslado;
import unqtravel.reservaYSusEstados.*;

public class GestorDeReservasTest {

    private GestorDeReservas gestor;
    private RegistroComprobante registro;

    private Reserva r1;
    private Reserva r2;
    private Reserva r3;

    @BeforeEach
    void setUp() {
        // DOC
        registro = mock(RegistroComprobante.class);

        r1 = mock(Reserva.class);
        r2 = mock(Reserva.class);
        r3 = mock(Reserva.class);

        // SUT
        gestor = new GestorDeReservas(registro);

        // Stubs
        when(r1.getId()).thenReturn("R1");
        when(r2.getId()).thenReturn("R2");
        when(r3.getId()).thenReturn("R3");
    }
    
    @Test
    void testCrearReserva() {
        String id = "R4";
        LocalDate fechaInicio = LocalDate.of(2026, 12, 1);
        LocalDate fechaFin = LocalDate.of(2026, 12, 5);
        int cantidad = 2;
        Direccion direccion = new Direccion();
        MetodoTraslado metodoTraslado = mock(MetodoTraslado.class);
        int cupoDeGuias = 1;
        String email = "cliente@mail.com";

        Reserva reservaCreada = gestor.crearReserva(
            id, fechaInicio, fechaFin, cantidad,
            direccion, metodoTraslado, cupoDeGuias, email
        );

        // Verificamos que se haya creado correctamente
        assertNotNull(reservaCreada);
        assertEquals(id, reservaCreada.getId());
        assertEquals(fechaInicio, reservaCreada.getFechaInicio());
        assertEquals(fechaFin, reservaCreada.getFechaFin());
        assertEquals(cantidad, reservaCreada.getCantidad());
        assertEquals(direccion, reservaCreada.getDireccionDestino());
        assertEquals(metodoTraslado, reservaCreada.getMetodoDeTraslado());
        assertEquals(cupoDeGuias, reservaCreada.getCupoDeGuias());
        assertEquals(email, reservaCreada.getEmail());

        // Verificamos que también se haya registrado
        assertSame(reservaCreada, gestor.buscarReserva(id));
        assertEquals(1, gestor.obtenerReservas().size());
    }
    
    @Test
    void testConstructor() {
        assertTrue(gestor.obtenerReservas().isEmpty());
    }

    @Test
    void testConstructorConRegistroNulo() {
        assertThrows(IllegalArgumentException.class,() -> new GestorDeReservas(null));
    }
    @Test
    void testRegistrarReserva() {
        gestor.registrarReserva(r1);

        assertEquals(1, gestor.obtenerReservas().size());
        assertSame(r1, gestor.buscarReserva("R1"));
    }

    @Test
    void testRegistrarReservaNula() {
        assertThrows(IllegalArgumentException.class,
            () -> gestor.registrarReserva(null));
    }

    @Test
    void testRegistrarReservaConIdDuplicado() {
        gestor.registrarReserva(r1);

        Reserva otraReserva = mock(Reserva.class);
        when(otraReserva.getId()).thenReturn("R1");

        assertThrows(IllegalArgumentException.class,() -> gestor.registrarReserva(otraReserva));
    }
    @Test
    void testBuscarReservaExistente() {
        gestor.registrarReserva(r1);

        assertSame(r1, gestor.buscarReserva("R1"));
    }

    @Test
    void testBuscarReservaInexistente() {
        assertNull(gestor.buscarReserva("R99"));
    }
    @Test
    void testObtenerReservas() {
        gestor.registrarReserva(r1);
        gestor.registrarReserva(r2);
        gestor.registrarReserva(r3);

        assertEquals(3, gestor.obtenerReservas().size());
    }

    @Test
    void testObtenerReservasDevuelveUnaCopia() {
        gestor.registrarReserva(r1);

        List<Reserva> reservas = gestor.obtenerReservas();
        reservas.clear();

        assertEquals(1, gestor.obtenerReservas().size());
    }
    @Test
    void testBuscarReservasPorPeriodo() {
        LocalDate inicio = LocalDate.of(2026, 11, 1);
        LocalDate fin = LocalDate.of(2026, 11, 30);

        when(r1.getFechaDeReserva()).thenReturn(LocalDate.of(2026, 11, 10));
        when(r2.getFechaDeReserva()).thenReturn(LocalDate.of(2026, 12, 5));
        when(r3.getFechaDeReserva()).thenReturn(LocalDate.of(2026, 11, 20));

        gestor.registrarReserva(r1);
        gestor.registrarReserva(r2);
        gestor.registrarReserva(r3);

        List<Reserva> resultado = gestor.buscarReservasPorPeriodo(inicio, fin);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r3));
        assertFalse(resultado.contains(r2));
    }

    @Test
    void testBuscarReservasPorPeriodoIncluyeLimites() {
        LocalDate inicio = LocalDate.of(2026, 11, 1);
        LocalDate fin = LocalDate.of(2026, 11, 30);

        when(r1.getFechaDeReserva()).thenReturn(inicio);
        when(r2.getFechaDeReserva()).thenReturn(fin);

        gestor.registrarReserva(r1);
        gestor.registrarReserva(r2);

        List<Reserva> resultado = gestor.buscarReservasPorPeriodo(inicio, fin);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r2));
    }

    @Test
    void testBuscarReservasPorPeriodoSinCoincidencias() {
        LocalDate inicio = LocalDate.of(2026, 11, 1);
        LocalDate fin = LocalDate.of(2026, 11, 30);

        when(r1.getFechaDeReserva()).thenReturn(LocalDate.of(2026, 10, 15));

        gestor.registrarReserva(r1);

        assertTrue(gestor.buscarReservasPorPeriodo(inicio, fin).isEmpty());
    }
}