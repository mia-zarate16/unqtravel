package unqtravel.metodoDePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransferenciaBancaria extends MetodoDePago {
    private final APITransferenciaBancaria api;
    private final String clave;
    private final String alias;
    private final List<ComprobanteTransferencia> comprobantes = new ArrayList<>();

    public TransferenciaBancaria(APITransferenciaBancaria api, String clave, String alias) {
        this.api = api;
        this.clave = clave;
        this.alias = alias;
        
    }


    @Override
    protected void validarDatos(double monto) {
        if (!api.validarCuenta(clave, alias)) { // le consulto a la api en base a los atributos
            throw new IllegalStateException(
                    "El CBU/CVU o el alias son invalidos"
            );
        }
    }

    @Override
    protected void reservarFondos(double monto) {
        // no hace nada pq hace la transferencia directa
    }

    @Override
    protected String ejecutarTransaccion(double monto) {
        return api.transferir(clave,monto);
    }

    @Override
    protected void notificarResultado(String codigo) {
        super.notificarResultado(codigo); // lo registra
        api.generarComprobante(codigo); // lo genera
    }
    
    private List<ComprobanteTransferencia> getComprobantes() {
    	return Collections.unmodifiableList(comprobantes);
    }
}
