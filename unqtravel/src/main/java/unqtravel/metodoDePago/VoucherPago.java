package unqtravel.metodoDePago;

public class VoucherPago {
	 
    private final String codigoTransaccion;
 
    public VoucherPago(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }
 
    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }
}