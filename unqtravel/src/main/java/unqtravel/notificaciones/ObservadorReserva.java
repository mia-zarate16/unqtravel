package unqtravel.notificaciones;

import unqtravel.reservaYSusEstados.Reserva;

public interface ObservadorReserva {

    void alConfirmar(Reserva reserva);

    void alIniciar(Reserva reserva);

    void alFinalizar(Reserva reserva);

    void alCancelar(Reserva reserva);
}
