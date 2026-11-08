package unqtravel.reservaYSusEstados;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;

public class GestorDeReservas {
  private Map<String,Reserva> listaReservas;
  
  public void registrarReserva(Reserva reserva) {
	if (listaReservas.containsKey(reserva.getId())) {
	  throw new IllegalArgumentException("Ya existe una reserva con ese ID");
	}
	    listaReservas.put(reserva.getId(), reserva);
  }
  
  // private Reserva buscarReserva(String id) { return; }
  
  private List<Reserva> filtrarPorPeriodo(LocalDate fechaInicio,LocalDate fechaFin) {
	return listaReservas.values().stream()
		                         .filter(reserva ->
		                                 !reserva.getFechaReserva().isBefore(fechaInicio) &&
		                                 !reserva.getFechaReserva().isAfter(fechaFin)
		                        ).toList();
  }
  
  private Map<EntidadCatalogo, Integer> contarApariciones(List<Reserva> reservas) {
	Map<EntidadCatalogo, Integer> cantidades = new HashMap<>();
    for (Reserva reserva : reservas) {
	  for (EntidadCatalogo item : reserva.getItems()) { cantidades.merge(item, 1, Integer::sum); }
	}
    return cantidades;
  }
  
  public List<Map.Entry<EntidadCatalogo, Integer>> ordenarPorApariciones(List<Reserva> reservas) {
    Map<EntidadCatalogo, Integer> cantidades = contarApariciones(reservas);

    return cantidades.entrySet().stream()
		                        .sorted(Map.Entry.<EntidadCatalogo, Integer>comparingByValue()
		                        .reversed()
		                       ).toList();
  }
}
