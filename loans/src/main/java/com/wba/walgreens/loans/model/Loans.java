package com.wba.walgreens.loans.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
/*
 * @Setter
 * 
 * @Getter
 * 
 * @ToString
 */
@Data
public class Loans {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "loan_number")
	private long loanNo;
	@Column(name = "customer_id")
	private int customerId;
	@Column(name = "start_date")
	private Date startDt;
	@Column(name = "loan_type")
	private String loan_Type;
	@Column(name = "total_loan")
	private int totalAmount;
	@Column(name = "amount_paid")
	private int amount_Paid;
	@Column(name = "outstanding_amount")
	private int otstanding_Amount;
	@Column(name = "create_date")
	private String create_Dt;
	

}
