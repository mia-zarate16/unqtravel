package reserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.metodoDeTrasladoYTasas.MetodoTraslado;
import unqtravel.reservaYSusEstados.Direccion;
import unqtravel.reservaYSusEstados.Reserva;
import unqtravel.reservaYSusEstados.SolicitudPendiente;

class ReservaTest {

    private Reserva reserva;
    private String id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidad;
    private Direccion direccion;
    private MetodoTraslado metodoTraslado;
    private int cupoDeGuias;
    private RegistroComprobante registro;
    private String email;
    
    //DOC
    private EntidadCatalogo item1;
    private EntidadCatalogo item2;

    @BeforeEach
    void setUp() {
        id = "R1";
        fechaInicio = LocalDate.of(2026, 12, 1);
        fechaFin = LocalDate.of(2026, 12, 5);
        cantidad = 2;
        direccion = new Direccion();
        metodoTraslado = mock(MetodoTraslado.class);
        cupoDeGuias = 1;
        registro = new RegistroComprobante();
        email = "cliente@mail.com";
        
        item1 = mock(EntidadCatalogo.class);

        reserva = crearReservaValida();
    }

    private Reserva crearReservaValida() {
        return new Reserva(
            id,
            fechaInicio,
            fechaFin,
            cantidad,
            direccion,
            metodoTraslado,
            cupoDeGuias,
            registro,
            email
        );
    }

    // CONSTRUCTOR

    @Test
    void creaUnaReservaConDatosValidos() {
        assertNotNull(reserva);
    }

    @Test
    void inicializaLaReservaEnEstadoSolicitudPendiente() {
        assertEquals("SolicitudPendiente", reserva.getEstadoActual().getClass().getSimpleName());
    }

    @Test
    void inicializaLaListaDeItemsVacia() {
        assertTrue(reserva.getItems().isEmpty());
    }

    // GETTERS

    @Test
    void devuelveElId() {
        assertEquals(id, reserva.getId());
    }

    @Test
    void devuelveLaFechaDeInicio() {
        assertEquals(fechaInicio, reserva.getFechaInicio());
    }

    @Test
    void devuelveLaFechaDeFin() {
        assertEquals(fechaFin, reserva.getFechaFin());
    }

    @Test
    void devuelveLaCantidad() {
        assertEquals(cantidad, reserva.getCantidad());
    }

    @Test
    void devuelveLaDireccionDestino() {
        assertSame(direccion, reserva.getDireccionDestino());
    }

    @Test
    void devuelveElMetodoDeTraslado() {
        assertSame(metodoTraslado, reserva.getMetodoDeTraslado());
    }

    @Test
    void devuelveElCupoDeGuias() {
        assertEquals(cupoDeGuias, reserva.getCupoDeGuias());
    }

    @Test
    void devuelveElRegistroDeComprobantes() {
        assertSame(registro, reserva.getRegistroDeComprobantes());
    }

    @Test
    void devuelveElEmail() {
        assertEquals(email, reserva.getEmail());
    }

    @Test
    void devuelveUnaListaDeItemsVaciaSinPermitirModificarLaInterna() {
        reserva.getItems().add(mock(unqtravel.catalogo.EntidadCatalogo.class));

        assertTrue(reserva.getItems().isEmpty());
    }
   
    @Test
    void agregarYQuitarItemsEnEstadoPendiente() {
    	reserva.getEstadoActual().agregarServicio(reserva,item1);
    	assertFalse(reserva.getItems().isEmpty());
    	assertThrows(IllegalArgumentException.class, () -> reserva.getEstadoActual().agregarServicio(reserva,item1));
    	assertThrows(IllegalArgumentException.class, () -> reserva.getEstadoActual().agregarServicio(reserva,null));
    	
    	reserva.getEstadoActual().quitarServicio(reserva,item1);
    	assertTrue(reserva.getItems().isEmpty());
    }
    
}
