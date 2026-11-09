package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public class GeneradorFactura implements ObservadorReserva {

    private final ComprobanteFiscal comprobanteFiscal;

    public GeneradorFactura(ComprobanteFiscal comprobanteFiscal) {
        this.comprobanteFiscal = comprobanteFiscal;
    }

    @Override
    public void alFinalizar(Reserva reserva) {
        comprobanteFiscal.generarComprobante(reserva);
        comprobanteFiscal.generarVoucher(reserva);
    }

    @Override
    public void alConfirmar(Reserva reserva) {}

    @Override
    public void alIniciar(Reserva reserva) {}

    @Override
    public void alCancelar(Reserva reserva) {}
}