package unqtravel.metodoDePago;


import unqtravel.comprobantes.Comprobante;
import unqtravel.comprobantes.VoucherPago;


public class TarjetaDeCredito extends MetodoDePago {
	 
    private final APITarjetaDeCredito api;
    private final String numero;
    private final String cvv;
    private final String vencimiento;
 
    public TarjetaDeCredito(APITarjetaDeCredito api, String numero, String cvv, String vencimiento) {
        this.api = api;
        this.numero = numero;
        this.cvv = cvv;
        this.vencimiento = vencimiento;
    }
 
    @Override
	public void validarDatos(double monto) {
        if (!api.validarTarjeta(numero, cvv, vencimiento)) {
            throw new PagoInvalidoException("Los datos de la tarjeta son invalidos");
        }
    }
 
    @Override
    public void reservarFondos(double monto) {
        api.preautorizar(numero, monto); // pre-autorizacion al banco emisor
    }
 
    @Override
    public String ejecutarTransaccion(double monto) {
        return api.cobrar(numero, monto);
    }
 
    @Override
    public void notificarResultado(String codigo) {
        super.notificarResultado(codigo); 
        api.generarVoucher(codigo);       
    }
    
    @Override
    public Comprobante crearComprobante(String codigo) {
        return new VoucherPago(codigo);
    }
}
 
