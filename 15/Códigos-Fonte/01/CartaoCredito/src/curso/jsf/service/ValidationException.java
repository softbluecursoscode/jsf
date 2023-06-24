package curso.jsf.service;

/**
 * Exce��o lan�ada quando ocorre uma valida��o de neg�cio
 */
public class ValidationException extends Exception {

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
