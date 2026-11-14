package comprobantes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.comprobantes.*;
import unqtravel.reservaYSusEstados.Reserva; // Ajustar al paquete real de Reserva

public class ComprobantesTest {

    private RegistroComprobante registro;
    private ComprobanteTransferencia transferencia;
    private VoucherPago pago;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        registro = new RegistroComprobante();
        reserva = mock(Reserva.class);

        transferencia = new ComprobanteTransferencia("OP-123", "CLAVE-456");
        pago = new VoucherPago("TX-789");
    }
    
    @Test
    void testConstructor() {
        assertTrue(registro.obtenerComprobantes().isEmpty());
    }

    @Test
    void testRegistrarComprobante() {
        registro.registrarComprobante(pago);

        assertEquals(1, registro.obtenerComprobantes().size());
        assertTrue(registro.obtenerComprobantes().contains(pago));
    }

    @Test
    void testRegistrarComprobanteNulo() {
        assertThrows(IllegalArgumentException.class, () -> registro.registrarComprobante(null));
    }

    @Test
    void testRegistrarComprobanteConIdentificadorDuplicado() {
        registro.registrarComprobante(pago);

        VoucherPago otroPago = new VoucherPago("TX-789");

        assertThrows(IllegalArgumentException.class, () -> registro.registrarComprobante(otroPago));
    }

    @Test
    void testExisteComprobante() {
        registro.registrarComprobante(pago);

        assertTrue(registro.existeComprobante("TX-789"));
        assertFalse(registro.existeComprobante("TX-000"));
    }

    @Test
    void testObtenerComprobantes() {
        registro.registrarComprobante(pago);
        registro.registrarComprobante(transferencia);

        List<Comprobante> comprobantes = registro.obtenerComprobantes();

        assertEquals(2, comprobantes.size());
        assertTrue(comprobantes.contains(pago));
        assertTrue(comprobantes.contains(transferencia));
    }

    @Test
    void testObtenerComprobantesDevuelveUnaCopia() {
        registro.registrarComprobante(pago);

        List<Comprobante> comprobantes = registro.obtenerComprobantes();
        comprobantes.clear();

        assertEquals(1, registro.obtenerComprobantes().size());
    }

    @Test
    void testGetNumeroOperacion() {
        assertEquals("OP-123", transferencia.getNumeroOperacion());
    }

    @Test
    void testGetClave() {
        assertEquals("CLAVE-456", transferencia.getClave());
    }

    @Test
    void testGetIdentificadorTransferencia() {
        assertEquals("OP-123", transferencia.getIdentificador());
    }
    
    @Test
    void testGetIdentificadorVoucherPago() {
        assertEquals("TX-789", pago.getIdentificador());
    }

    @Test
    void testConstructorVoucherReembolsoConImporte() {
        VoucherReembolso voucher = new VoucherReembolso(reserva, 1500.0f);

        assertEquals(reserva, voucher.getReserva());
        assertEquals(Optional.of(1500.0f), voucher.getImporte());
        assertNotNull(voucher.getIdentificador());
        assertFalse(voucher.getIdentificador().isBlank());
    }

    @Test
    void testConstructorVoucherReembolsoSinImporte() {
        VoucherReembolso voucher = new VoucherReembolso(reserva);

        assertEquals(reserva, voucher.getReserva());
        assertEquals(Optional.empty(), voucher.getImporte());
        assertNotNull(voucher.getIdentificador());
        assertFalse(voucher.getIdentificador().isBlank());
    }

    @Test
    void testConstructorVoucherReembolsoInvalidos() {
        assertThrows(IllegalArgumentException.class,() -> new VoucherReembolso(null, 1500.0f));

        assertThrows(IllegalArgumentException.class,() -> new VoucherReembolso(null));
        
        assertThrows(IllegalArgumentException.class,() -> new VoucherReembolso(reserva, 0.0f));

        assertThrows(IllegalArgumentException.class,() -> new VoucherReembolso(reserva, -100.0f));
    }

    @Test
    void testIdentificadoresDeVouchersDiferentes() {
        VoucherReembolso voucher1 = new VoucherReembolso(reserva);
        VoucherReembolso voucher2 = new VoucherReembolso(reserva);

        assertNotEquals(voucher1.getIdentificador(), voucher2.getIdentificador());
    }

}