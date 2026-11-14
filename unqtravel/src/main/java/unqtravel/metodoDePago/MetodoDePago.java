package unqtravel.metodoDePago;
import unqtravel.comprobantes.Comprobante;


public abstract class MetodoDePago {
	 
    private String codigoTransaccion;
 

    public String procesarPago(double monto) {
        validarDatos(monto);
        reservarFondos(monto);
        String codigo = ejecutarTransaccion(monto);
        notificarResultado(codigo);
        return codigo;
    }
 
  
    protected abstract void validarDatos(double monto);
 
    protected abstract void reservarFondos(double monto);
 
    protected abstract String ejecutarTransaccion(double monto);
 

    protected void notificarResultado(String codigo) {
        this.codigoTransaccion = codigo;
    }
 
    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }
    
    public abstract Comprobante crearComprobante(String codigo);
    
}
 