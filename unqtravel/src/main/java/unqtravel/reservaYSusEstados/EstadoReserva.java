package unqtravel.reservaYSusEstados;

import unqtravel.catalogo.EntidadCatalogo;

public abstract class EstadoReserva {
	
	void agregarServicio(Reserva reserva, EntidadCatalogo servicio) {
		throw new OperacionReservaInvalida("No se puede agregar un servicio a la reserva en el estado actual.");
	}
	
	void quitarServicio(Reserva reserva, EntidadCatalogo servicio) {
		throw new OperacionReservaInvalida("No se puede quitar un servicio a la reserva en el estado actual.");
	}
	
	void confirmarViaje(Reserva reserva) {
		throw new OperacionReservaInvalida("No se puede confirmar la reserva en el estado actual.");
	}
	
	void realizarCheckin(Reserva reserva) {
        throw new OperacionReservaInvalida("No se puede realizar el check-in en el estado actual.");
    }
	
	void iniciarViaje(Reserva reserva) {
        throw new OperacionReservaInvalida("No se puede iniciar el viaje en el estado actual.");
    }
	
	void finalizarViaje(Reserva reserva) {
        throw new OperacionReservaInvalida("No se puede finalizar el viaje en el estado actual.");
    }
	
	void cancelarViaje(Reserva reserva) {
		throw new OperacionReservaInvalida("No se puede cancelar la reserva en el estado actual.");
	}
}
