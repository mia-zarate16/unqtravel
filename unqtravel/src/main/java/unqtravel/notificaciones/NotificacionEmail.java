package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public class NotificacionEmail implements ObservadorReserva {

    private final MailSender mailSender;

    public NotificacionEmail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void mandarMail(String cambioDeEstado) {
        mailSender.enviarMail(
            "cliente@mail.com",
            "Actualización de reserva",
            "La reserva fue " + cambioDeEstado,
            null
        );
    }

    @Override
    public void alConfirmar(Reserva reserva) {
        mandarMail("confirmada");
    }

    @Override
    public void alIniciar(Reserva reserva) {
        mandarMail("iniciada");
    }

    @Override
    public void alFinalizar(Reserva reserva) {
        mandarMail("finalizada");
    }

    @Override
    public void alCancelar(Reserva reserva) {}
}