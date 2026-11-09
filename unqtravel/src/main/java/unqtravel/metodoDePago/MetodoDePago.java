package unqtravel.metodoDePago;


public abstract class MetodoDePago {
	 
    private String codigoTransaccion;
 

    public final String procesarPago(double monto) {
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
}
 