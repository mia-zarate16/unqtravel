package unqtravel.Reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.reservaYSusEstados.Reserva;

public class ReporteServiciosMasReservados implements Reporte {
	private List<Reserva> reservas;
	private Map<EntidadCatalogo, Integer> cantidadReservas;

    public ReporteServiciosMasReservados(List<Reserva> reservas) {
        this.reservas = new ArrayList<>(reservas);
        this.cantidadReservas = asociarItemsConCantidad();
    }
    
    private Map<EntidadCatalogo,Integer> asociarItemsConCantidad() {
    	Map<EntidadCatalogo, Integer> cantidades = new HashMap<>();

        for (Reserva reserva : reservas) {
            for (EntidadCatalogo item : reserva.getItems()) {
                cantidades.merge(item, 1, Integer::sum);
            }
        }

        return cantidades;
    }
    
    private Map<EntidadCatalogo,Integer> ordenarItems() {
    	Map<EntidadCatalogo, Integer> ordenado = new LinkedHashMap<>();

        cantidadReservas.entrySet().stream()
            .sorted(Map.Entry.<EntidadCatalogo, Integer>comparingByValue().reversed())
            .forEach(entrada ->
                ordenado.put(entrada.getKey(), entrada.getValue())
            );

        return ordenado;
    }
    
    public Map<EntidadCatalogo,Integer> getOrdenados() { return this.ordenarItems(); }

	@Override
	public String aceptar(VisitorReporte visitante) {
		return visitante.visitar(this);
	}

}
