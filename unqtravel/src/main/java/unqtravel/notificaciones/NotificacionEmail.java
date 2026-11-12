package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public class NotificacionEmail implements ObservadorReserva {

    private final MailSender mailSender;

    public NotificacionEmail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void mandarMail(Reserva reserva, String cambioDeEstado) {
        mailSender.enviarMail(
            reserva.getEmail(),
            "Actualización de reserva",
            "La reserva fue " + cambioDeEstado,
            null
        );
    }

    @Override
    public void alConfirmar(Reserva reserva) {
        mandarMail(reserva, "confirmada");
    }

    @Override
    public void alIniciar(Reserva reserva) {
        mandarMail(reserva, "iniciada");
    }

    @Override
    public void alFinalizar(Reserva reserva) {
        mandarMail(reserva, "finalizada");
    }

    @Override
    public void alCancelar(Reserva reserva) {}
}