package unqtravel.metodoDePago;

public interface APIBilleteraVirtual {
    boolean tieneSaldoSuficiente(String cuenta, double monto);

    void bloquearSaldo(String cuenta, double monto);

    String acreditar(String cuenta, double monto);

    void enviarNotificacionPush(String codigo);

}
