package com.nttdata.apliclient.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.feignclients.TransactionFeignClient;

import models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao ClientDao;
	
	@Autowired
	private TransactionFeignClient feignClient;
	
	@Override
	public Flux<Client> findAll() {
		// TODO Auto-generated method stub
		return ClientDao.findAll();
	}

	@Override
	public Mono<Client> findById(String id) {
		// TODO Auto-generated method stub
		return ClientDao.findById(id);
	}

	@Override
	public Mono<Client> save(Client client) {
		// TODO Auto-generated method stub
		return ClientDao.save(client);
	}

	@Override
	public Mono<Void> delete(Client client) {
		// TODO Auto-generated method stub
		return ClientDao.delete(client);
	}


	@Override
	public Flux<Transaction> listTransactionClient(String codeClient, String codeTransaction) {
		// TODO Auto-generated method stub
	   
		//return feignClient.listTransactionClient(codeClient, codeTransaction);
		
	  return Flux.fromIterable(feignClient.listTransactionClient(codeClient, codeTransaction));
	
	}
	
	

}
