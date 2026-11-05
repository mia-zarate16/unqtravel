package unqtravel.metodoDePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TarjetaDeCredito extends MetodoDePago {
	private final APITarjetaDeCredito api;
    private final String numero;
    private final String cvv;
    private final String vencimiento;
    private final List<VoucherPago> vouchers = new ArrayList<>();


    public TarjetaDeCredito(APITarjetaDeCredito api, String numero, String cvv, String vencimiento) {
        this.api = api;
        this.numero= numero;
        this.cvv= cvv;
        this.vencimiento= vencimiento;
    }

    @Override
    protected void validarDatos(double monto) {
        if (!api.validarTarjeta(numero, cvv, vencimiento)) { // le consulto a la api en base a los atributos
            throw new IllegalStateException(
                    "Los datos de la tarjeta son inválidos"
            );
        }
    }

    @Override
    protected void reservarFondos(double monto) {
        api.preautorizar(numero,monto); // aca preaurizo con el metodo de la api
    }

    @Override
    protected String ejecutarTransaccion(double monto) {
        return api.cobrar(numero,monto);
    }

    @Override
    protected void notificarResultado(String codigo) {
        super.notificarResultado(codigo); // lo registra
        api.generarVoucher(codigo); // lo genera
    }
    
    public List<VoucherPago> getVouchers() {
    	return Collections.unmodifiableList(vouchers);
    }
}
