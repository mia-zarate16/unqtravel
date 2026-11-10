package metodoDePago;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDePago.APIBilleteraVirtual;
import unqtravel.metodoDePago.BilleteraVirtual;
import unqtravel.metodoDePago.PagoInvalidoException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BilleteraVirtualTest {

    private BilleteraVirtual billetera;
    private APIBilleteraVirtual api;

    private final String cuenta = "mi-cuenta";
    private final double monto = 15000.0;
    private final String codigo = "TX-123";

    @BeforeEach
    void setUp() {
        api = mock(APIBilleteraVirtual.class);
        billetera = new BilleteraVirtual(api, cuenta);
    }

    @Test
    void seValidanLosDatosDeLaBilleteraCorrectamente() {
        when(api.tieneSaldoSuficiente(cuenta, monto)).thenReturn(true);

        billetera.validarDatos(monto);

        verify(api).tieneSaldoSuficiente(cuenta, monto);
    }

    @Test
    void cuandoLaBilleteraNoTieneSaldoSuficienteSeLanzaUnaExcepcion() {
        when(api.tieneSaldoSuficiente(cuenta, monto)).thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> billetera.validarDatos(monto)
        );

        verify(api).tieneSaldoSuficiente(cuenta, monto);
    }

    @Test
    void seReservanLosFondosDeLaBilletera() {
        billetera.reservarFondos(monto);

        verify(api).bloquearSaldo(cuenta, monto);
    }

    @Test
    void seEjecutaLaTransaccionDeLaBilletera() {
        when(api.acreditar(cuenta, monto)).thenReturn(codigo);

        String resultado = billetera.ejecutarTransaccion(monto);

        assertEquals(codigo, resultado);
        verify(api).acreditar(cuenta, monto);
    }

    @Test
    void seNotificaElResultadoDeLaTransaccion() {
        billetera.notificarResultado(codigo);

        assertEquals(codigo, billetera.getCodigoTransaccion());
        verify(api).enviarNotificacionPush(codigo);
    }

    @Test
    void seProcesaUnPagoCorrectamente() {
        when(api.tieneSaldoSuficiente(cuenta, monto)).thenReturn(true);
        when(api.acreditar(cuenta, monto)).thenReturn(codigo);

        String resultado = billetera.procesarPago(monto);

        assertEquals(codigo, resultado);
        assertEquals(codigo, billetera.getCodigoTransaccion());

        verify(api).tieneSaldoSuficiente(cuenta, monto);
        verify(api).bloquearSaldo(cuenta, monto);
        verify(api).acreditar(cuenta, monto);
        verify(api).enviarNotificacionPush(codigo);
    }

    @Test
    void cuandoLaValidacionFallaNoSeContinuaConElPago() {
        when(api.tieneSaldoSuficiente(cuenta, monto)).thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> billetera.procesarPago(monto)
        );

        verify(api).tieneSaldoSuficiente(cuenta, monto);
        verify(api, never()).bloquearSaldo(anyString(), anyDouble());
        verify(api, never()).acreditar(anyString(), anyDouble());
        verify(api, never()).enviarNotificacionPush(anyString());
    }
}
