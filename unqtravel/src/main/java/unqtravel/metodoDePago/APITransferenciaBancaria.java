package unqtravel.metodoDePago;

import java.time.LocalDate;

public interface APITransferenciaBancaria {
	 
    boolean validarCuenta(String clave, String alias);
 
    // Transferencia inmediata: devuelve el numero de operacion.
    String transferenciaDirecta(String clave, double monto);
 
    // Transferencia programada: la fecha le dice a la API cuando ejecutarla.
    String transferenciaProgramada(String clave, double monto, LocalDate fecha);
 
    void generarComprobante(String codigo);
}