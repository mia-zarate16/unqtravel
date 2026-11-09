package unqtravel.metodoDePago;

public class BilleteraVirtual extends MetodoDePago {
	 
    private final APIBilleteraVirtual api;
    private final String cuenta;
 
    public BilleteraVirtual(APIBilleteraVirtual api, String cuenta) {
        this.api = api;
        this.cuenta = cuenta;
    }
 
    @Override
    protected void validarDatos(double monto) {
        if (!api.tieneSaldoSuficiente(cuenta, monto)) {
            throw new PagoInvalidoException("Saldo insuficiente en la cuenta");
        }
    }
 
    @Override
    protected void reservarFondos(double monto) {
        api.bloquearSaldo(cuenta, monto); // el saldo queda bloqueado hasta confirmar
    }
 
    @Override
    protected String ejecutarTransaccion(double monto) {
        return api.acreditar(cuenta, monto); // acreditacion en tiempo real
    }
 
    @Override
    protected void notificarResultado(String codigo) {
        super.notificarResultado(codigo);    // registrarlo
        api.enviarNotificacionPush(codigo);  // notificacion push
    }
}
 
