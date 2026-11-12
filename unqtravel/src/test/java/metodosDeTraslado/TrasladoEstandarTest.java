package metodosDeTraslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDeTrasladoYTasas.PlataformaTransporte;
import unqtravel.metodoDeTrasladoYTasas.TrasladoEstandar;
import unqtravel.reservaYSusEstados.Direccion;
import unqtravel.reservaYSusEstados.Reserva;

class TrasladoEstandarTest {

    private PlataformaTransporte plataforma;
    private TrasladoEstandar trasladoEstandar;
    private Reserva reserva;
    private Direccion direccion;

    @BeforeEach
    void setUp() {
        plataforma = mock(PlataformaTransporte.class);
        trasladoEstandar = new TrasladoEstandar(plataforma);
        reserva = mock(Reserva.class);
        direccion = new Direccion();
    }

    @Test
    void calcularCostoDelegaEnLaPlataformaConLaCantidadYDireccionDeLaReserva() {
        when(reserva.getCantidad()).thenReturn(3);
        when(reserva.getDireccionDestino()).thenReturn(direccion);
        when(plataforma.estimarTraslado(3, direccion)).thenReturn(12000f);

        float resultado = trasladoEstandar.calcularCosto(reserva);

        assertEquals(12000f, resultado);
        verify(reserva).getCantidad();
        verify(reserva).getDireccionDestino();
        verify(plataforma).estimarTraslado(3, direccion);
    }
}
