package metodoDePago;

import org.junit.jupiter.api.Test;

import unqtravel.metodoDePago.ComprobanteTransferencia;

import static org.junit.jupiter.api.Assertions.*;

class ComprobanteTransferenciaTest {

    @Test
    void seCreaElComprobanteConLosDatosCorrectos() {
        String numeroOperacion = "OP-123";
        String clave = "1234567890123456789012";

        ComprobanteTransferencia comprobante =
            new ComprobanteTransferencia(numeroOperacion, clave);

        assertEquals(numeroOperacion, comprobante.getNumeroOperacion());
        assertEquals(clave, comprobante.getClave());
    }
}
