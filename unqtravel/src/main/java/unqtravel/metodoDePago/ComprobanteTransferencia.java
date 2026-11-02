package unqtravel.metodoDePago;

public class ComprobanteTransferencia {
	 
    private final String numeroOperacion;
    private final String clave;
 
    public ComprobanteTransferencia(String numeroOperacion, String clave) {
        this.numeroOperacion = numeroOperacion;
        this.clave = clave;
    }
 
    public String getNumeroOperacion() {
        return numeroOperacion;
    }
 
    public String getClave() {
        return clave;
    }
}