package mrs.domain.exception;

public class AlreadyReservedException extends RuntimeException{
	public AlreadyReservedException(String msg) {
		super(msg);
	}
}
