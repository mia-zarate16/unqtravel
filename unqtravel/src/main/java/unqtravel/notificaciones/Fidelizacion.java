package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public class Fidelizacion implements ObservadorReserva {

    private final Cupon cupon;
    private final MailSender mailSender;

    public Fidelizacion(Cupon cupon, MailSender mailSender) {
        this.cupon = cupon;
        this.mailSender = mailSender;
    }

    private void mandarCupon(Reserva reserva, String codigoCupon) {
        mailSender.enviarMail(
            reserva.getEmail(),
            "Cupón de descuento",
            "Te ofrecemos un cupón del 5%: " + codigoCupon,
            null
        );
    }

    @Override
    public void alCancelar(Reserva reserva) {
        String codigoCupon = cupon.generarCupon(reserva, 5);
        mandarCupon(reserva, codigoCupon);
    }

    @Override
    public void alConfirmar(Reserva reserva) {
    }

    @Override
    public void alIniciar(Reserva reserva) {
    }

    @Override
    public void alFinalizar(Reserva reserva) {
    }
}