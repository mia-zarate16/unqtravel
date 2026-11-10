package metodoDePago;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.metodoDePago.APITransferenciaBancaria;
import unqtravel.metodoDePago.PagoInvalidoException;
import unqtravel.metodoDePago.TransferenciaBancaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaBancariaTest {

    private TransferenciaBancaria transferencia;
    private APITransferenciaBancaria api;

    private final String clave = "1234567890123456789012";
    private final String alias = "mi.alias.banco";
    private final double monto = 15000.0;
    private final String codigo = "TR-123";
    private final LocalDate fecha = LocalDate.of(2026, 12, 15);

    @BeforeEach
    void setUp() {
        api = mock(APITransferenciaBancaria.class);
        transferencia = new TransferenciaBancaria(api, clave, alias);
    }

    @Test
    void seValidanLosDatosDeLaCuentaCorrectamente() {
        when(api.validarCuenta(clave, alias)).thenReturn(true);

        transferencia.validarDatos(monto);

        verify(api).validarCuenta(clave, alias);
    }

    @Test
    void cuandoLaCuentaEsInvalidaSeLanzaUnaExcepcion() {
        when(api.validarCuenta(clave, alias)).thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> transferencia.validarDatos(monto)
        );

        verify(api).validarCuenta(clave, alias);
    }

    @Test
    void noSeReservanFondosParaUnaTransferencia() {
        transferencia.reservarFondos(monto);

        verifyNoInteractions(api);
    }

    @Test
    void seEjecutaUnaTransferenciaDirecta() {
        when(api.transferenciaDirecta(clave, monto)).thenReturn(codigo);

        String resultado = transferencia.ejecutarTransaccion(monto);

        assertEquals(codigo, resultado);
        verify(api).transferenciaDirecta(clave, monto);
        verify(api, never()).transferenciaProgramada(
            anyString(), anyDouble(), any(LocalDate.class)
        );
    }

    @Test
    void seEjecutaUnaTransferenciaProgramada() {
        transferencia = new TransferenciaBancaria(api, clave, alias, fecha);

        when(api.transferenciaProgramada(clave, monto, fecha))
            .thenReturn(codigo);

        String resultado = transferencia.ejecutarTransaccion(monto);

        assertEquals(codigo, resultado);
        verify(api).transferenciaProgramada(clave, monto, fecha);
        verify(api, never()).transferenciaDirecta(anyString(), anyDouble());
    }

    @Test
    void seGeneraElComprobanteDeLaTransferencia() {
        transferencia.notificarResultado(codigo);

        assertEquals(codigo, transferencia.getCodigoTransaccion());
        verify(api).generarComprobante(codigo);
    }

    @Test
    void seProcesaUnaTransferenciaDirectaCorrectamente() {
        when(api.validarCuenta(clave, alias)).thenReturn(true);
        when(api.transferenciaDirecta(clave, monto)).thenReturn(codigo);

        String resultado = transferencia.procesarPago(monto);

        assertEquals(codigo, resultado);
        assertEquals(codigo, transferencia.getCodigoTransaccion());

        verify(api).validarCuenta(clave, alias);
        verify(api).transferenciaDirecta(clave, monto);
        verify(api).generarComprobante(codigo);
        verify(api, never()).transferenciaProgramada(
            anyString(), anyDouble(), any(LocalDate.class)
        );
    }

    @Test
    void seProcesaUnaTransferenciaProgramadaCorrectamente() {
        transferencia = new TransferenciaBancaria(api, clave, alias, fecha);

        when(api.validarCuenta(clave, alias)).thenReturn(true);
        when(api.transferenciaProgramada(clave, monto, fecha))
            .thenReturn(codigo);

        String resultado = transferencia.procesarPago(monto);

        assertEquals(codigo, resultado);
        assertEquals(codigo, transferencia.getCodigoTransaccion());

        verify(api).validarCuenta(clave, alias);
        verify(api).transferenciaProgramada(clave, monto, fecha);
        verify(api).generarComprobante(codigo);
        verify(api, never()).transferenciaDirecta(anyString(), anyDouble());
    }

    @Test
    void cuandoLaValidacionFallaNoSeRealizaLaTransferencia() {
        when(api.validarCuenta(clave, alias)).thenReturn(false);

        assertThrows(
            PagoInvalidoException.class,
            () -> transferencia.procesarPago(monto)
        );

        verify(api).validarCuenta(clave, alias);
        verify(api, never()).transferenciaDirecta(anyString(), anyDouble());
        verify(api, never()).transferenciaProgramada(
            anyString(), anyDouble(), any(LocalDate.class)
        );
        verify(api, never()).generarComprobante(anyString());
    }
}
