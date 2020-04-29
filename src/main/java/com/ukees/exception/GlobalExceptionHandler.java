package com.ukees.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {


	private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class);
	private static final String ACCESS_DENIED_CODE = "ACCESS_DENIED";
	private static final String ILLEGAL_ARGUMENT_CODE = "ILLEGAL_ARGUMENT";
	private static final String AUTH_FAILED_CODE = "AUTH_FAILED";
	private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({AccessDeniedException.class, BadCredentialsException.class})
	public ErrorResponse handleAccessDenied(RuntimeException ex) {
		log.warn(ACCESS_DENIED_CODE, ex);
		return new ErrorResponse(ex.getMessage(), ACCESS_DENIED_CODE);
	}

	@ResponseBody
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
		log.warn(ILLEGAL_ARGUMENT_CODE, ex);
		return new ErrorResponse(ex.getMessage(), ILLEGAL_ARGUMENT_CODE);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ErrorResponse handleAuthException(InternalAuthenticationServiceException ex) {
		log.warn(AUTH_FAILED_CODE, ex);
		return new ErrorResponse(ex.getMessage(), AUTH_FAILED_CODE);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleInternalException(Exception ex) {
		log.warn(INTERNAL_ERROR_CODE, ex);
		return new ErrorResponse(ex.getMessage(), INTERNAL_ERROR_CODE);
	}
}
