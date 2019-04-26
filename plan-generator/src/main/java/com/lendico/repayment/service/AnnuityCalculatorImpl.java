package com.lendico.repayment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.lendico.repayment.model.LoanPayload;

@Service
public class AnnuityCalculatorImpl implements AnnuityCalculator {

	private static final double PERCENT_CONVERSION = 100.0;
	private static final int MONTHS_IN_YEAR = 12;

	/*
	 * Formula used for calculating Annuity :
	 * 
	 * P = r(PV)/1- Math.pow((1+r),n) P = Annuity r = rate per period PV = present
	 * value of loan amount n = number of period
	 * 
	 * @see
	 * com.lendico.repayment.service.AnnuityCalculator#calculateAnnuity(java.lang.
	 * Double, java.math.BigDecimal, int)
	 */
	@Override
	public BigDecimal calculateAnnuity(BigDecimal loanAmount, Double interestRate, int duration) {
		Double nominalRatePerPeriod = (interestRate / PERCENT_CONVERSION) / (MONTHS_IN_YEAR);
		double numerator = loanAmount.doubleValue() * nominalRatePerPeriod;
		double denominator = 1 - Math.pow(1 + nominalRatePerPeriod, -duration);

		double annuity = numerator / denominator;
		BigDecimal annuityInBigDecimal = new BigDecimal(annuity);
		annuityInBigDecimal = annuityInBigDecimal.setScale(2, RoundingMode.HALF_EVEN);

		return annuityInBigDecimal;
	}

	@Override
	public void calculateAnnuity(LoanPayload payload) {
		BigDecimal annuity = this.calculateAnnuity(payload.getLoanAmount(), payload.getNominalRate().doubleValue(),
				payload.getDuration());
		payload.setAnnuity(annuity);
	}

}
