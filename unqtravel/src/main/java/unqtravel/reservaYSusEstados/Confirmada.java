package unqtravel.reservaYSusEstados;

import unqtravel.notificaciones.ObservadorReserva;

public class Confirmada extends EstadoReserva {
	
	@Override
    void realizarCheckin(Reserva reserva) {
        reserva.cambiarEstado(new EnCheckIn());
    }

    @Override
    void cancelarViaje(Reserva reserva) {
        reserva.liberarCupos();
        reserva.cambiarEstado(new Cancelada());
    }
    @Override
    void notificar(Reserva reserva, ObservadorReserva observador) {
        observador.alConfirmar(reserva);
    }
    
}
