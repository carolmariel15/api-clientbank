package com.nttdata.apliclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccount {
	

	private String accountNumber;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;

	private Date membershipDate;
	private Integer payDays;
	private Integer feeDue;
	private double balance;
	private double creditLimit;
	private Card card;

	private List<Transaction> listTransaction;

}
