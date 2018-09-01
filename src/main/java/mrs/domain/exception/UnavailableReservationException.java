package mrs.domain.exception;

public class UnavailableReservationException extends RuntimeException{
	public UnavailableReservationException(String msg) {
		super(msg);
	}
}
