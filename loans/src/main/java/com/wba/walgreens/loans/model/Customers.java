package com.wba.walgreens.loans.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Setter
@Getter @ToString
public class Customers {

	@Id
	@Column(name="customer_id")
    private int customerId;
	@Column(name="name")
    private String name;
	@Column(name="email")
    private String email;
	@Column(name="mobile_number")
    private String  mobileNo;
	@Column(name="create_date")
    private LocalDate createDt;

}
