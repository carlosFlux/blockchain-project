package com.packa.japp.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EmailNotFoundException extends AbstractThrowableProblem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -56955242601346919L;

	public EmailNotFoundException() {
		super(ErrorConstants.EMAIL_NOT_FOUND_TYPE, "Email address not registered", Status.BAD_REQUEST);
	}
}
