package unqtravel.reservaYSusEstados;

import unqtravel.notificaciones.ObservadorReserva;

public class Cancelada extends EstadoReserva {
	@Override
	void notificar(Reserva reserva, ObservadorReserva observador) {
	    observador.alCancelar(reserva);
	}
}
