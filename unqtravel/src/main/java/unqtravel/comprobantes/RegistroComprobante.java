package unqtravel.comprobantes;

import java.util.ArrayList;
import java.util.List;

public class RegistroComprobante {
	
	private List<Comprobante> comprobantes;
	
	
	//											CONSTRUCTOR
    public RegistroComprobante() {
        this.comprobantes = new ArrayList<>();
    }

    
    //											MÉTODOS DE COMPROBANTES
    public void registrarComprobante(Comprobante comprobante) {
        if (comprobante == null) {
            throw new IllegalArgumentException("El comprobante no puede ser nulo.");
        }
        if (this.existeComprobante(comprobante.getIdentificador())) {
            throw new IllegalArgumentException("Ya existe un comprobante con ese identificador.");
        }
        comprobantes.add(comprobante);
    }
    
    public boolean existeComprobante(String identificador) {
        return this.comprobantes.stream().anyMatch(c -> c.getIdentificador().equals(identificador));
    }

    public List<Comprobante> obtenerComprobantes() {
        return new ArrayList<>(comprobantes);
    }
    
}
