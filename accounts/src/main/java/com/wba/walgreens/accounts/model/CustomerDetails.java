package com.wba.walgreens.accounts.model;

import java.util.List;

import lombok.Data;
@Data
public class CustomerDetails {
	
	private Accounts accounts;
	private List<Loans> loans;
	private List<Cards> cards;

}
