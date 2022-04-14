package com.nttdata.apliclient.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientDao ClientDao;
	
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

}
