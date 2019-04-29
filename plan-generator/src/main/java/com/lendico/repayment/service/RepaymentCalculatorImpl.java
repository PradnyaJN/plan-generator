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
	public Repayment calculateRepayment(LoanPayload payload, Repayment prevRepayment, boolean isLastInstallment) {
		if (prevRepayment == null) {
			return calculateRepayment(payload, isLastInstallment);
		}
		return calculateRepayment(prevRepayment.getDate().plusMonths(1),
				prevRepayment.getRemainingOutstandingPrincipal(), payload.getAnnuity(), payload.getNominalRate(),
				isLastInstallment);
	}

	@Override
	public Repayment calculateRepayment(LoanPayload payload, boolean isLastInstallment) {
		return calculateRepayment(payload.getStartDateAsLocalDate(), payload.getLoanAmount(), payload.getAnnuity(),
				payload.getNominalRate(), isLastInstallment);
	}

	private Repayment calculateRepayment(LocalDateTime payoutDate, BigDecimal initialOutstandingPrincipal,
			BigDecimal borrowerPaymentAmount, BigDecimal nominalInterestRate, boolean isLastInstallment) {
		Repayment repayment = new Repayment();
		repayment.setDate(payoutDate);
		repayment.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
		repayment.setBorrowerPaymentAmount(borrowerPaymentAmount);
		repayment.setInterest(calculateInterest(repayment, nominalInterestRate));
		repayment.setPrincipal(calculatePrincipalAmount(repayment));
		BigDecimal outStandingPrincipal = calculateRemainingOutstandingPrincipal(repayment);
		repayment.setRemainingOutstandingPrincipal(outStandingPrincipal);

		if (isLastInstallment && !outStandingPrincipal.equals(BigDecimal.ZERO)) {
			Repayment adjustedRepayment = new Repayment();
			adjustedRepayment.setBorrowerPaymentAmount(borrowerPaymentAmount);
			adjustedRepayment.setDate(payoutDate);
			adjustedRepayment.setInitialOutstandingPrincipal(outStandingPrincipal);
			adjustedRepayment.setInterest(calculateInterest(adjustedRepayment, nominalInterestRate));
			adjustedRepayment.setPrincipal(calculatePrincipalAmount(adjustedRepayment));
			outStandingPrincipal = calculateRemainingOutstandingPrincipal(adjustedRepayment);
			adjustedRepayment.setRemainingOutstandingPrincipal(outStandingPrincipal);
			adjustedRepayment
					.setBorrowerPaymentAmount(adjustedRepayment.getInterest().add(adjustedRepayment.getPrincipal()));
			repayment.setBorrowerPaymentAmount(
					repayment.getBorrowerPaymentAmount().add(adjustedRepayment.getBorrowerPaymentAmount()));
			repayment.setInterest(repayment.getInterest().add(adjustedRepayment.getInterest()));
			repayment.setPrincipal(repayment.getPrincipal().add(adjustedRepayment.getPrincipal()));
			repayment.setRemainingOutstandingPrincipal(outStandingPrincipal);

		}
		return repayment;
	}

}
