package com.lendico.repayment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.RepaymentPlan;
import com.lendico.repayment.service.RepaymentPlanGenerator;

@RestController
@RequestMapping("/generate-plan")
public class PlanGeneratorController {

	@Autowired
	RepaymentPlanGenerator planGenerator;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RepaymentPlan generatePlan( @Valid @RequestBody LoanPayload payload) {
		return planGenerator.generateRepaymentPlan(payload);
	}

}
