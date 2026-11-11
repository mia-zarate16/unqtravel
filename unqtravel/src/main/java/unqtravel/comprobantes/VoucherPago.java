package unqtravel.comprobantes;

public class VoucherPago implements Comprobante {
	 
    private final String codigoTransaccion;
 
    public VoucherPago(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }
    
    @Override
    public String getIdentificador() {
        return codigoTransaccion;
    }
    
}