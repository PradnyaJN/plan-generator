package com.lendico.repayment.service;

import java.math.BigDecimal;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.Repayment;

public interface RepaymentCalculator {

	BigDecimal calculateInterest(Repayment repayment, BigDecimal interestRate);

	BigDecimal calculatePrincipalAmount(Repayment repayment);

	BigDecimal calculateRemainingOutstandingPrincipal(Repayment repayment);

	Repayment calculateRepayment(LoanPayload payload);

	Repayment calculateRepayment(LoanPayload payload, Repayment prevRepayment);

}
