package unqtravel.metodoDePago;


public interface APITransferenciaBancaria {

        boolean validarCuenta (String clave, String alias) ;

        String transferir(String clave, double monto);

        void generarComprobante(String codigo) ;
}
