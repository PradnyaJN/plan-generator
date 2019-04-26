package com.lendico.repayment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.Repayment;

@Service
public class RepaymentCalculatorImpl implements RepaymentCalculator {

	private static final BigDecimal PERCENTAGE = new BigDecimal("100").setScale(2);
	private static final BigDecimal DAYS_IN_YEAR = new BigDecimal("360").setScale(2);
	private static final BigDecimal DAYS_IN_MONTH = new BigDecimal("30").setScale(2);

	/*
	 * Formula for calculating interest Interest = (Nominal-Rate * Days in Month *
	 * Initial Outstanding Principal) / days in year
	 * 
	 * Assumption: Days in Month = 30 Days in Year = 360
	 * 
	 */
	@Override
	public BigDecimal calculateInterest(Repayment repayment, BigDecimal interestRate) {
		BigDecimal nominalRate = interestRate.divide(PERCENTAGE).multiply(DAYS_IN_MONTH);
		BigDecimal interest = repayment.getInitialOutstandingPrincipal()
				.multiply(new BigDecimal(nominalRate.toString()).setScale(2, RoundingMode.HALF_EVEN));
		interest = interest.divide(DAYS_IN_YEAR, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);

		return interest;
	}

	/*
	 * 
	 * principal = annuity-interest
	 */
	@Override
	public BigDecimal calculatePrincipalAmount(Repayment repayment) {
		BigDecimal principal = repayment.getBorrowerPaymentAmount().subtract(repayment.getInterest());
		if (repayment.getInitialOutstandingPrincipal().compareTo(principal) < 1) {
			principal = repayment.getInitialOutstandingPrincipal();
		}
		return principal;
	}

	/*
	 * Remaining Outstanding Principal = Initial outstanding principal - principal
	 */
	@Override
	public BigDecimal calculateRemainingOutstandingPrincipal(Repayment repayment) {
		BigDecimal remainingPrincipal = repayment.getInitialOutstandingPrincipal().subtract(repayment.getPrincipal());

		if (remainingPrincipal.compareTo(BigDecimal.ZERO) < 1) {
			remainingPrincipal = BigDecimal.ZERO.setScale(0, RoundingMode.HALF_EVEN);
		}
		return remainingPrincipal;
	}

	@Override
	public Repayment calculateRepayment(LoanPayload payload, Repayment prevRepayment) {
		if (prevRepayment == null) {
			return calculateRepayment(payload);
		}
		return calculateRepayment(prevRepayment.getDate().plusMonths(1),
				prevRepayment.getRemainingOutstandingPrincipal(), payload.getAnnuity(), payload.getNominalRate());
	}

	@Override
	public Repayment calculateRepayment(LoanPayload payload) {
		return calculateRepayment(payload.getStartDateAsLocalDate(), payload.getLoanAmount(), payload.getAnnuity(),
				payload.getNominalRate());
	}

	private Repayment calculateRepayment(LocalDateTime payoutDate, BigDecimal initialOutstandingPrincipal,
			BigDecimal borrowerPaymentAmount, BigDecimal nominalInterestRate) {
		Repayment repayment = new Repayment();
		repayment.setDate(payoutDate);
		repayment.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
		repayment.setBorrowerPaymentAmount(borrowerPaymentAmount);
		repayment.setInterest(calculateInterest(repayment, nominalInterestRate));
		repayment.setPrincipal(calculatePrincipalAmount(repayment));
		repayment.setRemainingOutstandingPrincipal(calculateRemainingOutstandingPrincipal(repayment));
		return repayment;
	}

}
