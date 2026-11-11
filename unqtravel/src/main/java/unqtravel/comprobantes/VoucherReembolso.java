package unqtravel.comprobantes;

import java.util.Optional;
import java.util.UUID;

import unqtravel.reservaYSusEstados.Reserva;

public class VoucherReembolso implements Comprobante {
	
	private String identificador;
    private Reserva reserva;
    private Optional<Float> importe;
    
    
    //											CONSTRUCTOR
    public VoucherReembolso(Reserva reserva, float importe) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }
        if (importe <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que cero.");
        }
        this.identificador = UUID.randomUUID().toString();
        this.reserva = reserva;
        this.importe = Optional.of(importe);
    }
    
    public VoucherReembolso(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }
        this.identificador = UUID.randomUUID().toString();
        this.reserva = reserva;
        this.importe = Optional.empty();
    }
    
    
    //											GETTERS
    @Override
    public String getIdentificador() {
        return identificador;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Optional<Float> getImporte() {
        return importe;
    }
    
}
