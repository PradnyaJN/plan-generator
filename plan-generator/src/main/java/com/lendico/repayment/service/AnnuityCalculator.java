package com.lendico.repayment.service;

import java.math.BigDecimal;

import com.lendico.repayment.model.LoanPayload;



public interface AnnuityCalculator {

	/**
	 * Calculate Annuity based on three input parameters
	 * @param loanAmount
	 * 		  Initial loan amount	
	 * @param interestRate
	 * 		  Rate of interest	
	 * @param duration
	 * 		  Duration in number of months for loan Repayment
	 * 
	 * @return Annuity amount to be paid every month 
	 */
	public BigDecimal calculateAnnuity(BigDecimal loanAmount,Double interestRate, int duration);

	public void calculateAnnuity(LoanPayload payload);

}


