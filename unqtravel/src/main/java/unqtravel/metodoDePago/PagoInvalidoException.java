package unqtravel.metodoDePago;

public class PagoInvalidoException extends RuntimeException {
	 
    public PagoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
