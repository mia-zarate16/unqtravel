package unqtravel.metodoDePago;

public interface APITarjetaDeCredito {
	 
    boolean validarTarjeta(String numero, String cvv, String vencimiento);
 
    void preautorizar(String numero, double monto);
 
    String cobrar(String numero, double monto);
 
    void generarVoucher(String codigo);
}
 