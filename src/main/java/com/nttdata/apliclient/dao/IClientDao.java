package com.nttdata.apliclient.dao;

import com.nttdata.apliclient.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IClientDao extends ReactiveMongoRepository<Client, String>{
	
	public Mono<Client> findByCodeClient(String codeClient);
	public Mono<Client> findByHoldersDni(String dni);
	public Mono<Client> findByHoldersDniAndHoldersPhone(String dni,String phone);

}
