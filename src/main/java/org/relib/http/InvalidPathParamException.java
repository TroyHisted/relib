package org.relib.http;

/**
 * Represents the situation where a method is configured to handle a request and one of the method
 * arguments is annotated with a path parameter whose value doesn't exist in the {@link HandleRequest}
 * value.
 *
 * @author Troy Histed
 */
public class InvalidPathParamException extends RuntimeException {

	/**
	 * Default constructor
	 */
	public InvalidPathParamException() {
		super();
	}

	/**
	 * Constructor with a message.
	 *
	 * @param message description
	 */
	public InvalidPathParamException(String message) {
		super(message);
	}

	/**
	 * Constructor with a message.
	 *
	 * @param cause exception
	 */
	public InvalidPathParamException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with a message.
	 *
	 * @param message description
	 * @param cause exception
	 */
	public InvalidPathParamException(String message, Throwable cause) {
		super(message, cause);
	}

}
