package com.lendico.repayment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoanPayload {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	@NotNull(message = "Duration cannot be null")
	@Positive(message = "Duration is invalid")
	private Integer duration;
	@NotNull(message = "Interest Rate cannot be null")
	@Positive(message = "Interest Rate is invalid")
	private BigDecimal nominalRate;

	@NotNull(message = "Loan Amount cannot be null")
	@Positive(message = "Loan Amount is invalid")
	private BigDecimal loanAmount;

	@JsonFormat(pattern = DATE_FORMAT)
	@DateTimeFormat(pattern = DATE_FORMAT)
	@NotNull(message = "Start Date can not be null")
	private Date startDate;
	private BigDecimal annuity;

	public LoanPayload(Integer duration, BigDecimal interestRate, BigDecimal principalAmount, Date payoutDate) {

		this.duration = duration;
		this.nominalRate = interestRate;
		this.loanAmount = principalAmount;
		this.startDate = payoutDate;
	}

	public LoanPayload() {
	}

	public final Integer getDuration() {
		return duration;
	}

	public final BigDecimal getNominalRate() {
		return nominalRate;
	}

	public final BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public final Date getStartDate() {
		return startDate;
	}

	public final void setDuration(Integer duration) {
		this.duration = duration;
	}

	public final void setNominalRate(BigDecimal nominalRate) {
		this.nominalRate = nominalRate;
	}

	public final void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final BigDecimal getAnnuity() {
		return annuity;
	}

	public final void setAnnuity(BigDecimal annuity) {
		this.annuity = annuity;
	}

	@JsonIgnore
	public final LocalDateTime getStartDateAsLocalDate() {
		return LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()).toLocalDate().atStartOfDay();
	}

}
