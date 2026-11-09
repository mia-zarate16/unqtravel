package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public interface ComprobanteFiscal {

	void generarComprobante(Reserva reserva);

	void generarVoucher(Reserva reserva);

}
