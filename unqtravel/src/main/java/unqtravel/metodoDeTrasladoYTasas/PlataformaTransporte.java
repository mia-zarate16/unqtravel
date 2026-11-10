package unqtravel.metodoDeTrasladoYTasas;

import unqtravel.reservaYSusEstados.Direccion;

public interface PlataformaTransporte {
	
	public float estimarTraslado(int cantidadPasajeros, Direccion direccionDestino);
}
