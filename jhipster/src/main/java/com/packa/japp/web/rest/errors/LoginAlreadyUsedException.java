package com.packa.japp.web.rest.errors;

public class LoginAlreadyUsedException extends BadRequestAlertException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8430210364966523210L;

	public LoginAlreadyUsedException() {
		super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login already in use", "userManagement", "userexists");
	}
}
