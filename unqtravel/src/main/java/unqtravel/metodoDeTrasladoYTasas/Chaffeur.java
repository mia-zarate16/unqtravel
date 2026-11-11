package unqtravel.metodoDeTrasladoYTasas;

import unqtravel.reservaYSusEstados.Reserva;

public class Chaffeur implements MetodoTraslado {
	
	private TrasladoVIP traslado;
	
	
	//										CONSTRUCTOR
	
	public Chaffeur(TrasladoVIP traslado) {
		super();
		this.traslado = traslado;
	}
	
	
	//									MÉTODO DEL STRATEGY
	@Override
	public float calcularCosto(Reserva reserva) {
		return traslado.calcularCosto(reserva.precioTotal());
	}


	
}
