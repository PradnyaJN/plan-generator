package com.lendico.repayment.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
	
	List<String> errorMessages = new ArrayList<String>();
	
	public void addErrorMessage(String error) {
		this.errorMessages.add(error);
	}

	public final List<String> getErrorMessages() {
		return errorMessages;
	}

	public final void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
