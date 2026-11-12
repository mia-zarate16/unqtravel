package unqtravel.reservaYSusEstados;
import unqtravel.notificaciones.ObservadorReserva;

import unqtravel.comprobantes.VoucherReembolso;

public class EnCurso extends EstadoReserva {
	
	@Override
    void finalizarViaje(Reserva reserva) {
        reserva.cambiarEstado(new Finalizada());
    }

    @Override
    void cancelarViaje(Reserva reserva) {
    	VoucherReembolso voucher = new VoucherReembolso(reserva);
        reserva.registrarComprobante(voucher);
        reserva.cambiarEstado(new Cancelada());
    }
    @Override
    void notificar(Reserva reserva, ObservadorReserva observador) {
        observador.alIniciar(reserva);
    }
}
