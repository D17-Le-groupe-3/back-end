package fr.diginamic.digiday.exceptions;

public class DigidayWebApiException extends RuntimeException {

	public DigidayWebApiException() {
    }

    public DigidayWebApiException(String message) {
        super(message);
    }

    public DigidayWebApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DigidayWebApiException(Throwable cause) {
        super(cause);
    }
}
