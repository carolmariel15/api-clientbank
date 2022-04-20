package com.nttdata.apliclient.service;

import com.nttdata.apliclient.models.Transaction;

import reactor.core.publisher.Flux;

public interface ITransactionService {
	
	public Flux<Transaction> listTransactionClientReact(String codeClient, String codeTransaction);

}
