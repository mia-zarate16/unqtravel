package metodosDeTraslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDeTrasladoYTasas.Chaffeur;
import unqtravel.metodoDeTrasladoYTasas.TrasladoVIP;
import unqtravel.reservaYSusEstados.Reserva;

class ChaffeurTest {

    private TrasladoVIP trasladoVIP;
    private Chaffeur chaffeur;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        trasladoVIP = mock(TrasladoVIP.class);
        chaffeur = new Chaffeur(trasladoVIP);
        reserva = mock(Reserva.class);
    }

    @Test
    void calcularCostoDelegaEnTrasladoVIPConElPrecioTotalDeLaReserva() {
        when(reserva.precioTotal()).thenReturn(10000f);
        when(trasladoVIP.calcularCosto(10000f)).thenReturn(15000f);

        float resultado = chaffeur.calcularCosto(reserva);

        assertEquals(15000f, resultado);
        verify(reserva).precioTotal();
        verify(trasladoVIP).calcularCosto(10000f);
    }
}
