package fr.diginamic.digiday.exceptions;

public class DigidayNotFoundException extends DigidayWebApiException {

	public DigidayNotFoundException() {
		super();
	}

	public DigidayNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DigidayNotFoundException(String message) {
		super(message);
	}

	public DigidayNotFoundException(Throwable cause) {
		super(cause);
	}
}
