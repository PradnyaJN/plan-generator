package com.lendico.repayment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lendico.repayment.model.ValidationError;

@ControllerAdvice
public class PlanGeneratorControllerExceptionHandler {

	private static final String INTERNAL_SERVER_ERROR = "Internal server error occured. Could not process request";
	private static final String NOT_READABLE = "Invalid / Not readable message recieved in request";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ValidationError> processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		ValidationError error = new ValidationError();
		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = fieldError.getDefaultMessage();
			error.addErrorMessage(localizedErrorMessage);
		}
		return new ResponseEntity<ValidationError>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ValidationError> messageNotReadableException(HttpMessageNotReadableException ex) {
		ValidationError error = new ValidationError();
		error.addErrorMessage(NOT_READABLE);
		return new ResponseEntity<ValidationError>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ValidationError> handleAllExceptions(Exception ex) {
		ValidationError error = new ValidationError();
		error.addErrorMessage(INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ValidationError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
