package unqtravel.metodoDePago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransferenciaBancaria extends MetodoDePago {
	 
    private final APITransferenciaBancaria api;
    private final String clave;              // CBU o CVU
    private final String alias;
    private final LocalDate fechaProgramada; // null = transferencia directa (inmediata)
 
    // Transferencia directa (inmediata).
    public TransferenciaBancaria(APITransferenciaBancaria api, String clave, String alias) {
        this(api, clave, alias, null);
    }
 
    // Transferencia programada para una fecha.
    public TransferenciaBancaria(APITransferenciaBancaria api, String clave, String alias, LocalDate fechaProgramada) {
        this.api = api;
        this.clave = clave;
        this.alias = alias;
        this.fechaProgramada = fechaProgramada;
    }
 
    @Override
    protected void validarDatos(double monto) {
        if (!api.validarCuenta(clave, alias)) {
            throw new PagoInvalidoException("El CBU/CVU o el alias son invalidos");
        }
    }
 
    @Override
    protected void reservarFondos(double monto) {
        // No aplica: es una transferencia directa, no hay fondos que reservar.
    }
 
    @Override
    protected String ejecutarTransaccion(double monto) {
        if (esProgramada()) {
            return api.transferenciaProgramada(clave, monto, fechaProgramada);
        }
        return api.transferenciaDirecta(clave, monto);
    }
 
    @Override
    protected void notificarResultado(String codigo) {
        super.notificarResultado(codigo); // registrarlo
        api.generarComprobante(codigo);   // generarlo
    }
 
    // Se deduce de la fecha: asi nunca puede quedar inconsistente con ella.
    public boolean esProgramada() {
        return fechaProgramada != null;
    }
}