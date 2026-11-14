package reserva;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unqtravel.catalogo.EntidadCatalogo;
import unqtravel.comprobantes.Comprobante;
import unqtravel.comprobantes.RegistroComprobante;
import unqtravel.comprobantes.VoucherReembolso;
import unqtravel.metodoDePago.MetodoDePago;
import unqtravel.metodoDeTrasladoYTasas.MetodoTraslado;
import unqtravel.notificaciones.ObservadorReserva;
import unqtravel.reservaYSusEstados.*;

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
    private ObservadorReserva observador;

    @BeforeEach
    void setUp() {
        id = "R1";
        fechaInicio = LocalDate.of(2026, 12, 1);
        fechaFin = LocalDate.of(2026, 12, 5);
        cantidad = 2;
        direccion = new Direccion();
        metodoTraslado = mock(MetodoTraslado.class);
        cupoDeGuias = 1;
        registro = mock(RegistroComprobante.class);
        email = "cliente@mail.com";
        
        item1 = mock(EntidadCatalogo.class);
        observador = mock(ObservadorReserva.class);
        
        when(item1.hayDisponibilidad(fechaInicio, fechaFin, cantidad)).thenReturn(true);
        when(item1.ocuparCupos(fechaInicio, fechaFin, cantidad)).thenReturn(true);

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

    @Test
    void testReservaConDatosValidos() {
        assertNotNull(reserva);
    }
    
    @Test
    void testConstructoresInvalidos() {
        assertThrows(IllegalArgumentException.class,
        		() -> new Reserva(null, fechaInicio, fechaFin, cantidad, direccion, metodoTraslado, cupoDeGuias, registro, email));
        
        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(" ", fechaInicio, fechaFin, cantidad,direccion, metodoTraslado, cupoDeGuias, registro, email));
        
        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, null, fechaFin, cantidad, direccion, metodoTraslado, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, null, cantidad, direccion, metodoTraslado, cupoDeGuias, registro, email));
        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaFin, fechaInicio, cantidad, direccion, metodoTraslado, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, 0, direccion, metodoTraslado, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, -1, direccion, metodoTraslado, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad, null, metodoTraslado, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad,direccion, null, cupoDeGuias, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad,direccion, metodoTraslado, -1, registro, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad,direccion, metodoTraslado, cupoDeGuias, null, email));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad,direccion, metodoTraslado, cupoDeGuias, registro, null));

        assertThrows(IllegalArgumentException.class,
            () -> new Reserva(id, fechaInicio, fechaFin, cantidad,direccion, metodoTraslado, cupoDeGuias, registro, " "));
    }

    @Test
    void inicializaLaReservaEnEstadoSolicitudPendiente() {
        assertInstanceOf(SolicitudPendiente.class, reserva.getEstadoActual());
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
    void calculaPrecioTotalDeLosItems() {
        EntidadCatalogo item2 = mock(EntidadCatalogo.class);

        when(item1.precioFinal()).thenReturn(100.0);
        when(item2.precioFinal()).thenReturn(250.0);

        reserva.agregarServicio(item1);
        reserva.agregarServicio(item2);

        assertEquals(350.0f, reserva.precioTotal(), 0.01f);
    }

    @Test
    void calculaPrecioTotalDeUnaReservaSinItems() {
        assertEquals(0.0f, reserva.precioTotal(), 0.01f);
    }
    
    @Test
    void calculaCostoDelTraslado() {
        when(metodoTraslado.calcularCosto(reserva)).thenReturn(50.0f);

        assertEquals(50.0f, reserva.costoDelTraslado(), 0.01f);
    }
    
    @Test
    void seleccionaMetodoDePagoValido() {
        MetodoDePago metodo = mock(MetodoDePago.class);

        assertDoesNotThrow(() -> reserva.seleccionarMetodoDePago(metodo));
    }

    @Test
    void noPermiteSeleccionarMetodoDePagoNulo() {
        assertThrows(IllegalArgumentException.class, () -> reserva.seleccionarMetodoDePago(null));
    }
    
    @Test
    void noPermiteAgregarObservadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> reserva.agregarObservador(null));
    }

    @Test
    void noPermiteAgregarDosVecesElMismoObservador() {
        reserva.agregarObservador(observador);

        assertThrows(IllegalArgumentException.class,() -> reserva.agregarObservador(observador));
    }
    
    @Test
    void testHayDisponibilidadEnPuntoDeEncuentro() {
        assertTrue(reserva.hayDisponibilidadEnPuntoDeEncuentro());
    }

    @Test
    void testNoHayDisponibilidadEnPuntoDeEncuentro() {
        Reserva reservaSinCupos = new Reserva(id, fechaInicio, fechaFin, cantidad, direccion, metodoTraslado, 0, registro, email);

        assertFalse(reservaSinCupos.hayDisponibilidadEnPuntoDeEncuentro());
    }
   
    @Test
    void agregarYQuitarItemsEnEstadoPendiente() {
    	reserva.agregarServicio(item1);
    	assertFalse(reserva.getItems().isEmpty());
    	assertThrows(IllegalArgumentException.class, () -> reserva.agregarServicio(item1));
    	assertThrows(IllegalArgumentException.class, () -> reserva.agregarServicio(null));
    	
    	reserva.quitarServicio(item1);
    	assertTrue(reserva.getItems().isEmpty());
    	assertThrows(IllegalArgumentException.class, () -> reserva.quitarServicio(item1));
    	assertThrows(IllegalArgumentException.class, () -> reserva.quitarServicio(null));
    }
    
    @Test
    void verificarVentaDeCupos() {
    	when(item1.hayDisponibilidad(fechaInicio, fechaFin, cantidad)).thenReturn(false);
    	assertThrows(OperacionReservaInvalida.class, () -> reserva.confirmarViaje());
    	
    	reserva.agregarServicio(item1);
    	assertThrows(OperacionReservaInvalida.class, () -> reserva.confirmarViaje());
    	
    	when(item1.hayDisponibilidad(fechaInicio, fechaFin, cantidad)).thenReturn(true);
    	when(item1.ocuparCupos(fechaInicio, fechaFin, cantidad)).thenReturn(false);
    	assertThrows(OperacionReservaInvalida.class, () -> reserva.confirmarViaje());
    	
    	when(item1.ocuparCupos(fechaInicio, fechaFin, cantidad)).thenReturn(true);
    	reserva.confirmarViaje();
    	assertInstanceOf(Confirmada.class, reserva.getEstadoActual());
    }
    
    @Test
    void verificarCancelacionDesdePendiente() {
    	reserva.cancelarViaje();
    	assertInstanceOf(Cancelada.class,reserva.getEstadoActual());
    }
    
    @Test
    void noPermiteRealizarCheckinEnEstadoPendiente() {
        assertThrows(OperacionReservaInvalida.class, () -> reserva.realizarCheckin());
        assertThrows(OperacionReservaInvalida.class, () -> reserva.iniciarViaje());
        assertThrows(OperacionReservaInvalida.class, () -> reserva.finalizarViaje());
    }
    
    @Test
    void noPermiteModificarItemsEnEstadoConfirmada() {
        reserva.agregarServicio(item1);
        reserva.confirmarViaje();

        assertThrows(OperacionReservaInvalida.class, () -> reserva.agregarServicio(mock(EntidadCatalogo.class)));
        assertThrows(OperacionReservaInvalida.class, () -> reserva.quitarServicio(item1));
    }
    
    @Test
    void notificaObservadoresAlConfirmarReserva() {
        reserva.agregarObservador(observador);

        reserva.agregarServicio(item1);
        reserva.confirmarViaje();

        verify(observador).alConfirmar(reserva);
    }
    
    @Test
    void verificarCancelacionDesdeConfirmada( ) {
    	reserva.agregarServicio(item1); reserva.confirmarViaje();
    	assertInstanceOf(Confirmada.class, reserva.getEstadoActual());
    	
    	reserva.cancelarViaje();
    	assertInstanceOf(Cancelada.class, reserva.getEstadoActual());
    }
    
    @Test
    void calculaImporteReembolsableEnCheckIn() {
        when(item1.precioFinal()).thenReturn(100.0);
        when(metodoTraslado.calcularCosto(reserva)).thenReturn(50.0f);

        reserva.agregarServicio(item1);

        assertEquals(150.0f, reserva.importeReembolsableEnCheckIn(), 0.01f);
    }
    
    @Test
    void verificarRegistroDeComprobanteAlCancelarDesdeCheckIn() {
    	when(metodoTraslado.calcularCosto(reserva)).thenReturn(100.0f);
    	
        reserva.agregarServicio(item1);
        reserva.confirmarViaje();
        reserva.realizarCheckin();

        reserva.cancelarViaje();

        verify(registro, times(1)).registrarComprobante(any(VoucherReembolso.class));
        verify(item1).liberarCupos(fechaInicio, fechaFin, cantidad);
    }
    
    @Test
    void notificaAlIniciarViaje() {
        reserva.agregarObservador(observador);

        reserva.agregarServicio(item1);
        reserva.confirmarViaje();
        reserva.realizarCheckin();
        reserva.iniciarViaje();

        verify(observador).alIniciar(reserva);
    }
    
    @Test
    void verificarCancelacionDesdeEnCurso() {
        reserva.agregarServicio(item1);
        reserva.confirmarViaje();
        reserva.realizarCheckin();
        reserva.iniciarViaje();

        assertInstanceOf(EnCurso.class, reserva.getEstadoActual());

        reserva.cancelarViaje();

        assertInstanceOf(Cancelada.class, reserva.getEstadoActual());
    }
    
    @Test
    void verificarRegistroDeComprobanteAlCancelarDesdeEnCurso() {
        reserva.agregarServicio(item1);
        reserva.confirmarViaje();
        reserva.realizarCheckin();
        reserva.iniciarViaje();

        reserva.cancelarViaje();
        
        verify(item1, never()).liberarCupos(fechaInicio, fechaFin, cantidad);
    }
    
    @Test
    void notificaAlFinalizarViaje() {
        reserva.agregarObservador(observador);

        reserva.agregarServicio(item1);
        reserva.confirmarViaje();
        reserva.realizarCheckin();
        reserva.iniciarViaje();
        reserva.finalizarViaje();

        verify(observador).alFinalizar(reserva);
    }
    
    @Test
    void notificaAlCancelarReserva() {
        reserva.agregarObservador(observador);

        reserva.cancelarViaje();

        verify(observador).alCancelar(reserva);
    }
    
    @Test
    void verificarVentasEfectivas() {
    	assertFalse(reserva.esVentaEfectiva());
    }
    
    @Test
    void testPagarSinMetodoDePago() {
        assertThrows(IllegalStateException.class, () -> reserva.pagar(1500.0));
    }
    
    @Test
    void testPagarCorrectamente() {
        MetodoDePago metodo = mock(MetodoDePago.class);
        Comprobante comprobante = mock(Comprobante.class);

        when(metodo.procesarPago(1500.0)).thenReturn("TX-123");
        when(metodo.crearComprobante("TX-123")).thenReturn(comprobante);

        reserva.seleccionarMetodoDePago(metodo);

        String codigo = reserva.pagar(1500.0);

        assertEquals("TX-123", codigo);

        verify(metodo).procesarPago(1500.0);
        verify(metodo).crearComprobante("TX-123");
        verify(registro).registrarComprobante(comprobante);
    }
}
