package metodoDePago;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDePago.APITarjetaDeCredito;
import unqtravel.metodoDePago.PagoInvalidoException;
import unqtravel.metodoDePago.TarjetaDeCredito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarjetaDeCreditoTest {

    private TarjetaDeCredito tarjeta;
    private APITarjetaDeCredito api;

    private final String numero = "1234567890123456";
    private final String cvv = "123";
    private final String vencimiento = "12/28";
    private final double monto = 15000.0;
    private final String codigo = "TX-456";

    @BeforeEach
    void setUp() {
        api = mock(APITarjetaDeCredito.class);
        tarjeta = new TarjetaDeCredito(api, numero, cvv, vencimiento);
    }

    @Test
    void seValidanLosDatosDeLaTarjetaCorrectamente() {
        when(api.validarTarjeta(numero, cvv, vencimiento))
            .thenReturn(true);

        tarjeta.validarDatos(monto);

        verify(api).validarTarjeta(numero, cvv, vencimiento);
    }

    @Test
    void cuandoLosDatosDeLaTarjetaSonInvalidosSeLanzaUnaExcepcion() {
        when(api.validarTarjeta(numero, cvv, vencimiento))
            .thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> tarjeta.validarDatos(monto)
        );

        verify(api).validarTarjeta(numero, cvv, vencimiento);
    }

    @Test
    void sePreautorizanLosFondosDeLaTarjeta() {
        tarjeta.reservarFondos(monto);

        verify(api).preautorizar(numero, monto);
    }

    @Test
    void seEjecutaElCobroDeLaTarjeta() {
        when(api.cobrar(numero, monto)).thenReturn(codigo);

        String resultado = tarjeta.ejecutarTransaccion(monto);

        assertEquals(codigo, resultado);
        verify(api).cobrar(numero, monto);
    }

    @Test
    void seGeneraElVoucherDeLaTransaccion() {
        tarjeta.notificarResultado(codigo);

        assertEquals(codigo, tarjeta.getCodigoTransaccion());
        verify(api).generarVoucher(codigo);
    }

    @Test
    void seProcesaUnPagoConTarjetaCorrectamente() {
        when(api.validarTarjeta(numero, cvv, vencimiento))
            .thenReturn(true);
        when(api.cobrar(numero, monto)).thenReturn(codigo);

        String resultado = tarjeta.procesarPago(monto);

        assertEquals(codigo, resultado);
        assertEquals(codigo, tarjeta.getCodigoTransaccion());

        verify(api).validarTarjeta(numero, cvv, vencimiento);
        verify(api).preautorizar(numero, monto);
        verify(api).cobrar(numero, monto);
        verify(api).generarVoucher(codigo);
    }

    @Test
    void cuandoLaValidacionFallaNoSeContinuaConElPago() {
        when(api.validarTarjeta(numero, cvv, vencimiento))
            .thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> tarjeta.procesarPago(monto)
        );

        verify(api).validarTarjeta(numero, cvv, vencimiento);
        verify(api, never()).preautorizar(anyString(), anyDouble());
        verify(api, never()).cobrar(anyString(), anyDouble());
        verify(api, never()).generarVoucher(anyString());
    }
}
