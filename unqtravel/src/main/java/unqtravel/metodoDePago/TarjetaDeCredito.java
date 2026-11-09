package unqtravel.metodoDePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    protected void validarDatos(double monto) {
        if (!api.validarTarjeta(numero, cvv, vencimiento)) {
            throw new PagoInvalidoException("Los datos de la tarjeta son invalidos");
        }
    }
 
    @Override
    protected void reservarFondos(double monto) {
        api.preautorizar(numero, monto); // pre-autorizacion al banco emisor
    }
 
    @Override
    protected String ejecutarTransaccion(double monto) {
        return api.cobrar(numero, monto);
    }
 
    @Override
    protected void notificarResultado(String codigo) {
        super.notificarResultado(codigo); // registrarlo
        api.generarVoucher(codigo);       // generarlo
    }
}
 
