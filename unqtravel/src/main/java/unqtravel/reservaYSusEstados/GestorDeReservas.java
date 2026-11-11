package unqtravel.reservaYSusEstados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.metodoDeTrasladoYTasas.MetodoTraslado;

public class GestorDeReservas {
	
	private List<Reserva> reservas;
	private RegistroComprobante registroDeComprobantes;
	
	
	//										CONSTRUCTOR
	public GestorDeReservas(RegistroComprobante registroDeComprobantes) {
		super();
		if (registroDeComprobantes == null) {
            throw new IllegalArgumentException("El registro de comprobantes no puede ser nulo.");
		}
        this.reservas = new ArrayList<>();
        this.registroDeComprobantes = registroDeComprobantes;
	}
	
	
	//										MÉTODOS DE RESERVA
	public Reserva crearReserva(String id, LocalDate fechaInicio, LocalDate fechaFin, int cantidad, Direccion direccionDestino,
			MetodoTraslado metodoDeTraslado, int cupoDeGuias) {
		Reserva reserva = new Reserva(id, fechaInicio, fechaFin, cantidad,
	            direccionDestino, metodoDeTraslado, cupoDeGuias,this.registroDeComprobantes);
	    registrarReserva(reserva);
	    return reserva;
	}
	
	public void registrarReserva(Reserva reserva) {
		if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }
		if (buscarReserva(reserva.getId()) != null) {
			throw new IllegalArgumentException("Ya existe una reserva con ese identificador.");
		}
        this.reservas.add(reserva);
	}
	
	public Reserva buscarReserva(String id) {
        return this.reservas.stream()
            .filter(reserva -> reserva.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
	
	public List<Reserva> obtenerReservas() {
        return new ArrayList<>(this.reservas);
    }
}
