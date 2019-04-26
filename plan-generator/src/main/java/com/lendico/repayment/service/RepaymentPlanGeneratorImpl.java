package com.lendico.repayment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.model.RepaymentPlan;

@Service
public class RepaymentPlanGeneratorImpl implements RepaymentPlanGenerator {

	@Autowired
	AnnuityCalculator annuityCalculator;

	@Autowired
	RepaymentCalculator repaymentCalculator;

	@Override
	public RepaymentPlan generateRepaymentPlan(LoanPayload payload) {
		List<Repayment> repayments = new ArrayList<Repayment>();
		annuityCalculator.calculateAnnuity(payload);

		Repayment preRepayment = null;
		for (int i = 0; i < payload.getDuration(); i++) {
			Repayment repayment = repaymentCalculator.calculateRepayment(payload, preRepayment);
			preRepayment = repayment;
			System.out.println(repayment.toString());
			repayments.add(preRepayment);
		}

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setRepaymentPlan(repayments);
		return repaymentPlan;
	}

}
