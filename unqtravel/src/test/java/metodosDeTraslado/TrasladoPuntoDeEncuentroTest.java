package metodosDeTraslado;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDeTrasladoYTasas.TrasladoPuntoDeEncuentro;
import unqtravel.reservaYSusEstados.Reserva;

class TrasladoPuntoDeEncuentroTest {

    private TrasladoPuntoDeEncuentro traslado;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        traslado = new TrasladoPuntoDeEncuentro();
        reserva = mock(Reserva.class);
    }

    @Test
    void calcularCostoDevuelveCero() {
        assertEquals(0f, traslado.calcularCosto(reserva));
    }

    @Test
    void hayDisponibilidadDevuelveTrueCuandoLaReservaTieneCupos() {
        when(reserva.hayDisponibilidadEnPuntoDeEncuentro()).thenReturn(true);

        assertTrue(traslado.hayDisponibilidad(reserva));

        verify(reserva).hayDisponibilidadEnPuntoDeEncuentro();
    }

    @Test
    void hayDisponibilidadDevuelveFalseCuandoLaReservaNoTieneCupos() {
        when(reserva.hayDisponibilidadEnPuntoDeEncuentro()).thenReturn(false);

        assertFalse(traslado.hayDisponibilidad(reserva));

        verify(reserva).hayDisponibilidadEnPuntoDeEncuentro();
    }
}
