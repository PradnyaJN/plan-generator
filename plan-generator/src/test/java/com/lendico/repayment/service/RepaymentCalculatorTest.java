/**
 * 
 */
package com.lendico.repayment.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.Repayment;

/**
 * @author parsh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepaymentCalculatorTest {


	@Autowired
	RepaymentCalculator repaymentCalculator;

	LoanPayload loanPayload;

	private Repayment firstRepayment;

	@Before
	public void setUp() throws Exception {
		loanPayload = new LoanPayload(24, new BigDecimal("5.0"), new BigDecimal("5000.0"), new Date(System.currentTimeMillis()));
		loanPayload.setAnnuity(new BigDecimal("219.36"));

		firstRepayment = new Repayment();
		firstRepayment.setBorrowerPaymentAmount(loanPayload.getAnnuity());
		firstRepayment.setInitialOutstandingPrincipal(loanPayload.getLoanAmount());
		firstRepayment.setDate(loanPayload.getStartDateAsLocalDate());

	}

	public final void setLoanPayload(LoanPayload loanPayload) {
		this.loanPayload = loanPayload;
	}

	public final void setFirstRepayment(Repayment firstRepayment) {
		this.firstRepayment = firstRepayment;
	}

	public final LoanPayload getLoanPayload() {
		return loanPayload;
	}

	public final Repayment getFirstRepayment() {
		return firstRepayment;
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.lendico.repayment.service.RepaymentCalculatorImpl#calculateInterest()}.
	 */
	@Test
	public final void testCalculateInterest() {
		assertEquals(new BigDecimal("20.83"),
				repaymentCalculator.calculateInterest(firstRepayment, loanPayload.getNominalRate()));
	}

	/**
	 * Test method for
	 * {@link com.lendico.repayment.service.RepaymentCalculatorImpl#calculatePrincipalAmount()}.
	 */
	@Test
	public final void testCalculatePrincipalAmount() {
		firstRepayment.setInterest(repaymentCalculator.calculateInterest(firstRepayment, loanPayload.getNominalRate()));
		assertEquals(new BigDecimal("198.53"), repaymentCalculator.calculatePrincipalAmount(firstRepayment));

	}

	/**
	 * Test method for
	 * {@link com.lendico.repayment.service.RepaymentCalculatorImpl#calculateRemainingOutstandingPrincipal()}.
	 */
	@Test
	public final void testCalculateRemainingOutstandingPrincipal() {
		firstRepayment.setInterest(repaymentCalculator.calculateInterest(firstRepayment, loanPayload.getNominalRate()));
		firstRepayment.setPrincipal(repaymentCalculator.calculatePrincipalAmount(firstRepayment));
		assertEquals(new BigDecimal("4801.47"),
				repaymentCalculator.calculateRemainingOutstandingPrincipal(firstRepayment));
	}

	/**
	 * Test method for
	 * {@link com.lendico.repayment.service.RepaymentCalculatorImpl#calculateRepayment(com.lendico.repayment.model.Repayment)}.
	 */
	@Test
	public final void testCalculateRepayment() {
		Repayment repayment = repaymentCalculator.calculateRepayment(loanPayload,null, false);
		assertEquals(new BigDecimal("20.83"), repayment.getInterest());
		assertEquals(new BigDecimal("198.53"), repayment.getPrincipal());
		assertEquals(new BigDecimal("4801.47"), repayment.getRemainingOutstandingPrincipal());

	}

}
