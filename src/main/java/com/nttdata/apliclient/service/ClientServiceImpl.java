package com.nttdata.apliclient.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.apliclient.controller.ClientController;
import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.feignclients.TransactionFeignClient;

import models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao ClientDao;
	
	@Autowired
	private TransactionFeignClient feignClient;
	
	private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
	
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

    //llamado de microservicio
	@Override
	public Flux<Transaction> listTransactionClient(String codeClient, String codeTransaction) {
        LOGGER.info("metod listTransactionClient : llamado al servicio de transation" );
		return Flux.fromIterable(feignClient.listTransactionClient(codeClient, codeTransaction));
	
	}
	
	

}
