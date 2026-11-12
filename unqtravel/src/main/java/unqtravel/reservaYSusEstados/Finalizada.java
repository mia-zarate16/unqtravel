package unqtravel.reservaYSusEstados;

import unqtravel.notificaciones.ObservadorReserva;

public class Finalizada extends EstadoReserva {
	@Override
	void notificar(Reserva reserva, ObservadorReserva observador) {
	    observador.alFinalizar(reserva);
	}
}
