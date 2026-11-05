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
            throw new IllegalStateException(
                    "La cuenta no tiene saldo suficiente"
            );
        }
    }
    @Override
    protected void reservarFondos(double monto) { //consultar
        api.bloquearSaldo(cuenta, monto);
    }

    @Override
    protected String ejecutarTransaccion(double monto) { //consultar
        return api.acreditar(cuenta, monto);
    }

    @Override
    protected void notificarResultado(String codigo) { //consultar
        super.notificarResultado(codigo);
        api.enviarNotificacionPush(codigo);
    }
}
