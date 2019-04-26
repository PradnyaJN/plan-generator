package com.lendico.repayment.service;

import com.lendico.repayment.model.LoanPayload;
import com.lendico.repayment.model.RepaymentPlan;

public interface RepaymentPlanGenerator {

	RepaymentPlan generateRepaymentPlan(LoanPayload payload);
}
