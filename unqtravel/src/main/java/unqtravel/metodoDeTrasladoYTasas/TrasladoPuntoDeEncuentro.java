package unqtravel.metodoDeTrasladoYTasas;

import unqtravel.reservaYSusEstados.Reserva;

public class TrasladoPuntoDeEncuentro implements MetodoTraslado {


	//									CONSTRUCTOR
	public TrasladoPuntoDeEncuentro() {
		super();
	}


	//									MÉTODO DEL STRATEGY
	@Override
	public float calcularCosto(Reserva reserva) {
		return 0;
	}

	
	//									OTROS
	
	public boolean hayDisponibilidad(Reserva reserva) {
		return reserva.getPuntoDeEncuentro().hayDisponibilidad();
	}
}
