package com.wba.walgreens.accounts.model;

import java.util.Date;

import lombok.Data;


@Data
public class Loans {
		
	
	private long loanNo;
	
	private int customerId;
	
	private Date startDt;

	private String loan_Type;
	
	private int totalAmount;
	
	private int amount_Paid;
	
	private int otstanding_Amount;
	
	private String create_Dt;
	

}
