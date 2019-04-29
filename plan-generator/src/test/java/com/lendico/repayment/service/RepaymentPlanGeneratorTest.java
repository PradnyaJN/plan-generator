package com.lendico.repayment.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.lendico.repayment.model.RepaymentPlan;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RepaymentPlanGeneratorTest {

	@Autowired
	RepaymentPlanGenerator planGenerator;

	@Autowired
	AnnuityCalculator annuityCalculator;

	private LoanPayload loanPayload1, loanPayload2;
	private static final Date TODAY = new Date(System.currentTimeMillis());

	@Before
	public void setUp() throws Exception {
		loanPayload1 = new LoanPayload(3, new BigDecimal("5.0"), new BigDecimal("100"), TODAY);
		loanPayload2 = new LoanPayload(24, new BigDecimal("5.0"), new BigDecimal("5000"), TODAY);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGenerateRepaymentPlan() {

		testGeneratePlan(loanPayload1);
		testGeneratePlan(loanPayload2);

	}

	private void testGeneratePlan(LoanPayload loanPayload) {
		RepaymentPlan repaymentPlan = planGenerator.generateRepaymentPlan(loanPayload);
		assertNotNull(repaymentPlan);
		assertNotNull(repaymentPlan.getRepaymentPlan());
		annuityCalculator.calculateAnnuity(loanPayload);
		BigDecimal initialAmount = loanPayload.getLoanAmount();

		BigDecimal principalPaid = BigDecimal.ZERO;
		BigDecimal remainingPrincipal = initialAmount;

		for (Repayment repayment : repaymentPlan.getRepaymentPlan()) {
			System.out.println(repayment.toString());
			principalPaid = principalPaid.add(repayment.getPrincipal());
			remainingPrincipal = remainingPrincipal.subtract(repayment.getPrincipal());

		}

		assertTrue(principalPaid.setScale(2, RoundingMode.HALF_DOWN).compareTo(initialAmount) == 0);
		assertTrue(remainingPrincipal.compareTo(BigDecimal.ZERO) == 0);
	}

}
