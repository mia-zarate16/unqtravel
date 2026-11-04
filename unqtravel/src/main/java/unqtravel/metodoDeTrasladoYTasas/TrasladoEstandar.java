package unqtravel.metodoDeTrasladoYTasas;

import unqtravel.reservaYSusEstados.Reserva;


public class TrasladoEstandar implements MetodoTraslado {
	
	private PlataformaTransporte plataforma;
	
	
	//									CONSTRUCTOR
	
	public TrasladoEstandar(PlataformaTransporte plataforma) {
		super();
		this.plataforma = plataforma;
	}


	
	//									MÉTODO DEL STRATEGY
	@Override
	public float calcularCosto(Reserva reserva) {
		int cantPasajeros = reserva.getCantidadPasajeros();
		Direccion direccion = reserva.getDireccion();
		return plataforma.estimarTraslado(cantPasajeros, direccion);
	}
	
}
