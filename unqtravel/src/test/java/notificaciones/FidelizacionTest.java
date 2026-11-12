package notificaciones;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unqtravel.notificaciones.Cupon;
import unqtravel.notificaciones.Fidelizacion;
import unqtravel.notificaciones.MailSender;
import unqtravel.reservaYSusEstados.Reserva;

public class FidelizacionTest {
	 private Cupon cupon;
	    private MailSender mailSender;
	    private Fidelizacion fidelizacion;
	    private Reserva reserva;

	    @BeforeEach
	    void setUp() {
	        cupon = mock(Cupon.class);
	        mailSender = mock(MailSender.class);
	        fidelizacion = new Fidelizacion(cupon, mailSender);
	        reserva = mock(Reserva.class);

	        when(reserva.getEmail()).thenReturn("cliente@mail.com");
	        when(cupon.generarCupon(reserva, 5)).thenReturn("CUPON-5");
	    }

	    @Test
	    void alCancelarGeneraCuponDelCincoPorCientoYLoEnviaPorMail() {
	        fidelizacion.alCancelar(reserva);

	        verify(cupon).generarCupon(reserva, 5);

	        verify(mailSender).enviarMail(
	            "cliente@mail.com",
	            "Cupón de descuento",
	            "Te ofrecemos un cupón del 5%: CUPON-5",
	            null
	        );
	    }

	    @Test
	    void alConfirmarNoGeneraNiEnviaCupon() {
	        fidelizacion.alConfirmar(reserva);

	        verificarQueNoSeGenereNiEnvíeCupon();
	    }

	    @Test
	    void alIniciarNoGeneraNiEnviaCupon() {
	        fidelizacion.alIniciar(reserva);

	        verificarQueNoSeGenereNiEnvíeCupon();
	    }

	    @Test
	    void alFinalizarNoGeneraNiEnviaCupon() {
	        fidelizacion.alFinalizar(reserva);

	        verificarQueNoSeGenereNiEnvíeCupon();
	    }

	    private void verificarQueNoSeGenereNiEnvíeCupon() {
	        verifyNoInteractions(cupon, mailSender);
	    }
	}