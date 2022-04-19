package com.nttdata.apliclient.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private IClientDao clientDao;

	@Autowired
	private TransactionFeignClient feignClient;

	private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);

	@Override
	public Flux<Client> findAll() {
		// TODO Auto-generated method stub
		return clientDao.findAll();
	}

	@Override
	public Mono<Client> findById(String id) {
		// TODO Auto-generated method stub
		return clientDao.findById(id);
	}

	@Override
	public Mono<Client> save(Client client) {
		// TODO Auto-generated method stub
		return clientDao.save(client);
	}

	@Override
	public Mono<Void> delete(Client client) {
		// TODO Auto-generated method stub
		return clientDao.delete(client);
	}

	// llamado de microservicio
	@Override
	public Flux<Transaction> listTransactionClient(String codeClient, String codeTransaction) {
		LOGGER.info("metod listTransactionClient : llamado al servicio de transation");
		return Flux.fromIterable(feignClient.listTransactionClient(codeClient, codeTransaction));

	}

	@Override
	public Mono<Client> findByCode(String code) {
		// TODO Auto-generated method stub
		return clientDao.findByCode(code);
	}

	@Override
	public Mono<Client> findByHoldersDni(String dni) {
		// TODO Auto-generated method stub
		return clientDao.findByHoldersDni(dni);
	}

	@Override
	public Mono<Client> findByHoldersDniAndHoldersPhone(String dni, String phone) {
		// TODO Auto-generated method stub
		return clientDao.findByHoldersDniAndHoldersPhone(dni, phone);
	}

}
