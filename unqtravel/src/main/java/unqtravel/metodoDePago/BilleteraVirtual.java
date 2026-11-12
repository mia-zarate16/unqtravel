package unqtravel.metodoDePago;

import unqtravel.comprobantes.Comprobante;
import unqtravel.comprobantes.VoucherPago;

public class BilleteraVirtual extends MetodoDePago {
	 
    private final APIBilleteraVirtual api;
    private final String cuenta;
 
    public BilleteraVirtual(APIBilleteraVirtual api, String cuenta) {
        this.api = api;
        this.cuenta = cuenta;
    }
 
    @Override
	public void validarDatos(double monto) {
        if (!api.tieneSaldoSuficiente(cuenta, monto)) {
            throw new PagoInvalidoException("Saldo insuficiente en la cuenta");
        }
    }
 
    @Override
	public void reservarFondos(double monto) {
        api.bloquearSaldo(cuenta, monto); // el saldo queda bloqueado hasta confirmar
    }
 
    @Override
	public String ejecutarTransaccion(double monto) {
        return api.acreditar(cuenta, monto); // acreditacion en tiempo real
    }
 
    @Override
	public void notificarResultado(String codigo) {
        super.notificarResultado(codigo);    // registrarlo
        api.enviarNotificacionPush(codigo);  // notificacion push
    }
    
    @Override
    public Comprobante crearComprobante(String codigo) {
        return new VoucherPago(codigo);
    }
}
 
