package com.lendico.repayment.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.model.RepaymentPlan;
import com.lendico.repayment.service.RepaymentPlanGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlanGeneratorControllerTest {

	@InjectMocks
	PlanGeneratorController planGeneratorController;

	@Mock
	RepaymentPlanGenerator planGenerator;

	private MockMvc mockMvc;
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(planGeneratorController).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGeneratePlan() {
		Date payoutDate = new Date(System.currentTimeMillis());
		LoanPayload payload = new LoanPayload(1, new BigDecimal("1.0"), new BigDecimal("100.0"), payoutDate);

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		Repayment repayment = new Repayment();
		repayment.setBorrowerPaymentAmount(new BigDecimal("100.0"));
		repayment.setDate(payload.getStartDateAsLocalDate());
		repayment.setInitialOutstandingPrincipal(new BigDecimal("100.0"));
		repayment.setRemainingOutstandingPrincipal(BigDecimal.ZERO);
		repayment.setInterest(new BigDecimal("50.00"));
		repayment.setPrincipal(new BigDecimal("50.00"));
		List<Repayment> repayments = new ArrayList<Repayment>();
		repayments.add(repayment);
		repaymentPlan.setRepaymentPlan(repayments);

		when(planGenerator.generateRepaymentPlan(payload)).thenReturn(repaymentPlan);

		try {
			mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/generate-plan")
					.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(payload)))
			.andExpect(status().isOk())
			;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * converts a Java object into JSON representation
	 */
	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
