package com.packa.japp.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidPasswordException extends AbstractThrowableProblem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6573604820163469312L;

	public InvalidPasswordException() {
		super(ErrorConstants.INVALID_PASSWORD_TYPE, "Incorrect password", Status.BAD_REQUEST);
	}
}
