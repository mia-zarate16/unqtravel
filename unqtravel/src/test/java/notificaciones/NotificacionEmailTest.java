
package notificaciones;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.notificaciones.MailSender;
import unqtravel.notificaciones.NotificacionEmail;
import unqtravel.reservaYSusEstados.Reserva;

class NotificacionEmailTest {

    private MailSender mailSender;
    private NotificacionEmail notificacionEmail;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        notificacionEmail = new NotificacionEmail(mailSender);
        reserva = mock(Reserva.class);

        when(reserva.getEmail()).thenReturn("cliente@mail.com");
    }

    @Test
    void alConfirmarEnviaMailAlEmailDeLaReserva() {
        notificacionEmail.alConfirmar(reserva);

        verify(mailSender).enviarMail(
            "cliente@mail.com",
            "Actualización de reserva",
            "La reserva fue confirmada",
            null
        );
    }

    @Test
    void alIniciarEnviaMailAlEmailDeLaReserva() {
        notificacionEmail.alIniciar(reserva);

        verify(mailSender).enviarMail(
            "cliente@mail.com",
            "Actualización de reserva",
            "La reserva fue iniciada",
            null
        );
    }

    @Test
    void alFinalizarEnviaMailAlEmailDeLaReserva() {
        notificacionEmail.alFinalizar(reserva);

        verify(mailSender).enviarMail(
            "cliente@mail.com",
            "Actualización de reserva",
            "La reserva fue finalizada",
            null
        );
    }

    @Test
    void alCancelarNoEnviaMail() {
        notificacionEmail.alCancelar(reserva);

        verifyNoInteractions(mailSender);
    }
}

