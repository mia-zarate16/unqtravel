package unqtravel.reservaYSusEstados;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.notificaciones.ObservadorReserva;

public class SolicitudPendiente extends EstadoReserva {
	
	@Override
	public boolean esVentaEfectiva() { return false; }
	
	@Override
	public void agregarServicio(Reserva reserva, EntidadCatalogo item) {
		reserva.agregarItem(item);
	}
	
	@Override
	public void quitarServicio(Reserva reserva, EntidadCatalogo item) {
		reserva.quitarItem(item);
	}
	
	@Override
	void confirmarViaje(Reserva reserva) {
		if (!reserva.ocuparCupos()) {
	        throw new OperacionReservaInvalida("No se puede confirmar la reserva ya que no hay disponibilidad suficiente.");
	    }
	    reserva.cambiarEstado(new Confirmada());
	}
	
	@Override
	void cancelarViaje(Reserva reserva) {
		reserva.cambiarEstado(new Cancelada());
	}
	
	@Override
	void notificar(Reserva reserva, ObservadorReserva observador) {}
	
}
