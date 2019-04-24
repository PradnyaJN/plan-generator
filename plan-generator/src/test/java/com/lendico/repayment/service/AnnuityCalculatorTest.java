/**
 * 
 */
package com.lendico.repayment.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author parsh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnuityCalculatorTest {

	@Autowired
	AnnuityCalculator annuityCalculator;
	private BigDecimal loanAmount;
	private double interestRate;
	private int duration;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		loanAmount = new BigDecimal("5000.0");
		interestRate = 5.0;
		duration = 24;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.lendico.repayment.service.AnnuityCalculatorImpl#calculateAnnuity(java.math.BigDecimal, java.lang.Double, int)}.
	 */
	@Test
	public final void testCalculateAnnuity() {
		assertEquals(BigDecimal.valueOf(219.36),
				annuityCalculator.calculateAnnuity(loanAmount, interestRate, duration));
	}

}
