package unqtravel.empresaDeViajesYTurismo;

import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.reservaYSusEstados.GestorDeReservas;

public class EmpresaDeViajesYTurismo {
	
	private RegistroComprobante registroComprobantes;
    private GestorDeReservas gestorDeReservas;
    
    
    //										CONSTRUCTOR
    public EmpresaDeViajesYTurismo() {
        this.registroComprobantes = new RegistroComprobante();
        this.gestorDeReservas = new GestorDeReservas(this.registroComprobantes);
    }
    
}
