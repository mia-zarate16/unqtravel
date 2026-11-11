package unqtravel.reservaYSusEstados;

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
    
}
