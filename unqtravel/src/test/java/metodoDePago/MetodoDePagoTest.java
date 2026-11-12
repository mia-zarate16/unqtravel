package metodoDePago;


import org.junit.jupiter.api.Test;

import unqtravel.comprobantes.Comprobante;
import unqtravel.comprobantes.VoucherPago;
import unqtravel.metodoDePago.MetodoDePago;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetodoDePagoTest {

    @Test
    void seEjecutanLasEtapasDelPagoEnElOrdenCorrecto() {
        List<String> pasos = new ArrayList<>();

        MetodoDePago metodo = new MetodoDePago() {

            @Override
            protected void validarDatos(double monto) {
                pasos.add("validar");
            }

            @Override
            protected void reservarFondos(double monto) {
                pasos.add("reservar");
            }

            @Override
            protected String ejecutarTransaccion(double monto) {
                pasos.add("ejecutar");
                return "TX-123";
            }

            @Override
            protected void notificarResultado(String codigo) {
                pasos.add("notificar");
                super.notificarResultado(codigo);
            }

            @Override
            public Comprobante crearComprobante(String codigo) {
                return new VoucherPago(codigo);
            }
        };
        String resultado = metodo.procesarPago(15000.0);

        assertEquals(
            List.of("validar", "reservar", "ejecutar", "notificar"),
            pasos
        );

        assertEquals("TX-123", resultado);
        assertEquals("TX-123", metodo.getCodigoTransaccion());
    }
}
