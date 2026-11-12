package unqtravel.reservaYSusEstados;

import unqtravel.comprobantes.VoucherReembolso;
import unqtravel.notificaciones.ObservadorReserva;

public class EnCheckIn extends EstadoReserva {
	
	@Override
    void iniciarViaje(Reserva reserva) {
        reserva.cambiarEstado(new EnCurso());
    }

    @Override
    void cancelarViaje(Reserva reserva) {
    	float importe = reserva.importeReembolsableEnCheckIn();
        VoucherReembolso voucher = new VoucherReembolso(reserva, importe);
        reserva.registrarComprobante(voucher);
    	reserva.liberarCupos();
        reserva.cambiarEstado(new Cancelada());
    }
    
    @Override
    void notificar(Reserva reserva, ObservadorReserva observador) {}
}
