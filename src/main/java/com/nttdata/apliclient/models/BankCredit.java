package com.nttdata.apliclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCredit {

	private String id;
	private String codeClient;
	private Currency currency;
	private TypeAccount typeAccount;
	@Nullable
	private Date requestDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	private Integer payDays;
	private Integer feeDue;
	private double amount;
	private Integer fee;

	private List<Transaction> listTransaction;
}
