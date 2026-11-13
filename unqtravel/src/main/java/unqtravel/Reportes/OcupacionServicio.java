package unqtravel.Reportes;

import java.time.LocalDate;

import unqtravel.catalogo.ServicioIndividual;

public class OcupacionServicio {
	private ServicioIndividual servicio;
    private LocalDate fecha;
    private int cuposTotales;
    private int cuposReservados;
    private int cuposLibres;
    
	public ServicioIndividual getServicio() {
		return servicio;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public int getCuposTotales() {
		return cuposTotales;
	}

	public int getCuposReservados() {
		return cuposReservados;
	}

	public int getCuposLibres() {
		return cuposLibres;
	}

	public OcupacionServicio(ServicioIndividual servicio, LocalDate fecha, int totales, int reservados, int libres) {
	  this.servicio = servicio;
	  this.fecha = fecha;
	  this.cuposTotales = totales;
	  this.cuposReservados = reservados;
	  this.cuposLibres = libres;
	}
}
