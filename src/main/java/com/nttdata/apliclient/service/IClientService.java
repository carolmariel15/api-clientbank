package com.nttdata.apliclient.service;

import java.util.List;

import com.nttdata.apliclient.document.Client;

import models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {
	
	//Flux y Mono son flujos de elementos, solamente que Flux te va emitir varios elementos
	
	//devueve varios elementos observable te 
	public Flux<Client> findAll();
	
	//devuelve un solo elemento obsevable
	public Mono<Client> findById(String id);
	
	//busca por codigo de cliente (DNI, RUC, otros)
	public Mono<Client> findByCode(String code);
	
	public Mono<Client> findByHoldersDni(String dni);
	
	public Mono<Client> findByHoldersDniAndHoldersPhone(String dni,String phone);
	
	public Mono<Client> save(Client client);
	
	public Mono<Void> delete(Client client);
	
	
	
	public Flux<Transaction> listTransactionClient(String codeClient, String codeTransaction);
		
	

}
