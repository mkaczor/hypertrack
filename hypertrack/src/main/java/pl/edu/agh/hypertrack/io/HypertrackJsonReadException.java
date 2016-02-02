package pl.edu.agh.hypertrack.io;

public class HypertrackJsonReadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HypertrackJsonReadException(String format, Object... args) {
		super(String.format(format, args));
	}
}
