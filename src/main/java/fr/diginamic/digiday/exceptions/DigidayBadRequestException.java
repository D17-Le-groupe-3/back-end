package fr.diginamic.digiday.exceptions;

public class DigidayBadRequestException extends DigidayWebApiException {

	public DigidayBadRequestException() {
		super();
	}

	public DigidayBadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public DigidayBadRequestException(String message) {
		super(message);
	}

	public DigidayBadRequestException(Throwable cause) {
		super(cause);
	}

}
