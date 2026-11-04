package unqtravel.catalogo;

import java.util.Objects;

public class Proveedor {
	
	private String nombre;
	private String datosContacto;
	
	
	//										CONSTRUCTOR
	
	public Proveedor(String nombre, String datosContacto) {
		super();
		this.nombre = Objects.requireNonNull(nombre);
		this.datosContacto = Objects.requireNonNull(datosContacto);
	}


	
	//										GETTERS
	public String getNombre() {
		return nombre;
	}

	public String getDatosContacto() {
		return datosContacto;
	}
	
	
}
