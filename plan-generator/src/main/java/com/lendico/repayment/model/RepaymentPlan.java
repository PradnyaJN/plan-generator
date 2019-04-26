package com.lendico.repayment.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;


public class RepaymentPlan {

	private List<Repayment> repaymentPlan;

    
	@JsonValue  
	public final List<Repayment> getRepaymentPlan() {
		return repaymentPlan;
	}

	public final void setRepaymentPlan(List<Repayment> repaymentPlan) {
		this.repaymentPlan = repaymentPlan;
	}
}
