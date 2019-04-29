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
		for (int i = 1; i <= payload.getDuration(); i++) {
			boolean isLastInstallment = (i == payload.getDuration() ) ? true : false;
			System.out.println("isLast"+isLastInstallment);
			Repayment repayment = repaymentCalculator.calculateRepayment(payload, preRepayment, isLastInstallment);
			preRepayment = repayment;
			System.out.println(repayment.toString());
			repayments.add(preRepayment);
		}

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setRepaymentPlan(repayments);
		return repaymentPlan;
	}

}
