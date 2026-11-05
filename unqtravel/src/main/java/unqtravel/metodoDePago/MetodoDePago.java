package unqtravel.metodoDePago;

import java.util.ArrayList;
import java.util.List;

public abstract class MetodoDePago {
	private final List<String> codigosDeTransaccion = new ArrayList<>();

    public final String procesarPago(double monto) {

        if (!Double.isFinite(monto) || monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor a cero"
            );
        }

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
        codigosDeTransaccion.add(codigo);
    }

    public List<String> getCodigosDeTransaccion() {
        return List.copyOf(codigosDeTransaccion);
    }
}
