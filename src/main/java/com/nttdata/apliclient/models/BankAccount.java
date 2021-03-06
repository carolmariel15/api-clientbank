package com.nttdata.apliclient.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
	
	private String accountNumber;
	private String typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;

	private Date membershipDate;
	private double balance;
	private Card card;
	private List<Transaction> listTransaction;

}
