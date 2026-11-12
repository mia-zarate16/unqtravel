
package notificaciones;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.notificaciones.ComprobanteFiscal;
import unqtravel.notificaciones.GeneradorFactura;
import unqtravel.reservaYSusEstados.Reserva;

class GeneradorFacturaTest {

    private ComprobanteFiscal comprobanteFiscal;
    private GeneradorFactura generadorFactura;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        comprobanteFiscal = mock(ComprobanteFiscal.class);
        generadorFactura = new GeneradorFactura(comprobanteFiscal);
        reserva = mock(Reserva.class);
    }

    @Test
    void alFinalizarGeneraComprobanteYVoucher() {
        generadorFactura.alFinalizar(reserva);

        verify(comprobanteFiscal).generarComprobante(reserva);
        verify(comprobanteFiscal).generarVoucher(reserva);
    }

    @Test
    void alConfirmarNoGeneraComprobantes() {
        generadorFactura.alConfirmar(reserva);

        verificarQueNoSeGenerenComprobantes();
    }

    @Test
    void alIniciarNoGeneraComprobantes() {
        generadorFactura.alIniciar(reserva);

        verificarQueNoSeGenerenComprobantes();
    }

    @Test
    void alCancelarNoGeneraComprobantes() {
        generadorFactura.alCancelar(reserva);

        verificarQueNoSeGenerenComprobantes();
    }

    private void verificarQueNoSeGenerenComprobantes() {
        verifyNoInteractions(comprobanteFiscal);
    }
}
