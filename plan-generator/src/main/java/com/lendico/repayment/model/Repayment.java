package com.lendico.repayment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Repayment {
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private BigDecimal borrowerPaymentAmount;
	// yyyy-MM-dd'T'HH:mm:ss'Z'
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonFormat(pattern = DATE_FORMAT)
	private LocalDateTime date;
	private BigDecimal initialOutstandingPrincipal;
	private BigDecimal interest;
	private BigDecimal principal;
	private BigDecimal remainingOutstandingPrincipal;

	public final BigDecimal getBorrowerPaymentAmount() {
		return borrowerPaymentAmount;
	}

	public final void setBorrowerPaymentAmount(BigDecimal borrowerPaymentAmount) {
		this.borrowerPaymentAmount = borrowerPaymentAmount;
	}

	public final LocalDateTime getDate() {
		return date;
	}

	public final void setDate(LocalDateTime date) {
		this.date = date;
	}

	public final BigDecimal getInitialOutstandingPrincipal() {
		return initialOutstandingPrincipal;
	}

	public final void setInitialOutstandingPrincipal(BigDecimal initialOutstandingPrincipal) {
		this.initialOutstandingPrincipal = initialOutstandingPrincipal;
	}

	public final BigDecimal getInterest() {
		return interest;
	}

	public final void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public final BigDecimal getPrincipal() {
		return principal;
	}

	public final void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public final BigDecimal getRemainingOutstandingPrincipal() {
		return remainingOutstandingPrincipal;
	}

	public final void setRemainingOutstandingPrincipal(BigDecimal remainingOutstandingPrincipal) {
		this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
	}

	@Override
	public String toString() {
		return "Repayment [borrowerPaymentAmount=" + borrowerPaymentAmount + ", date=" + date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
				+ ", initialOutstandingPrincipal=" + initialOutstandingPrincipal + ", interest=" + interest
				+ ", principal=" + principal + ", remainingOutstandingPrincipal=" + remainingOutstandingPrincipal + "]";
	}

}
