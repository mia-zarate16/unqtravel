package metodoDePago;

import org.junit.jupiter.api.Test;

import unqtravel.metodoDePago.VoucherPago;

import static org.junit.jupiter.api.Assertions.*;

class VoucherPagoTest {

    @Test
    void seCreaElVoucherConElCodigoDeTransaccionCorrecto() {
        String codigoTransaccion = "GOJO-SATORU";

        VoucherPago voucher = new VoucherPago(codigoTransaccion);

        assertEquals(codigoTransaccion, voucher.getCodigoTransaccion());
    }
}
