package com.nttdata.apliclient.service.impl;

import com.nttdata.apliclient.models.BankAccount;
import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.util.Constants;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.models.Transaction;
import com.nttdata.apliclient.service.IClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {


	private final IClientDao clientDao;
	private final ReactiveCircuitBreakerFactory factory;

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
	public Mono<Client> findByCodeClient(String codeClient) {
		// TODO Auto-generated method stub
		return clientDao.findByCodeClient(codeClient);
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

	@Override
	public Flux<BankAccount> findAllBankAccount() {
		return WebClient.create(Constants.PATH_GATEWAY)
				.get()
				.uri(Constants.PATH_SERVICE_BANKACCOUNT_URI)
				.retrieve()
				.bodyToFlux(BankAccount.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = factory.create("services");
					return rcb.run(it, throwable -> Flux.just(new BankAccount()));
				});
	}

	@Override
	public Mono<Response> saveBankAccount(BankAccount bankAccount) {
		return WebClient.create(Constants.PATH_GATEWAY).post()
				.uri(Constants.PATH_SERVICE_BANKACCOUNT_URI)
				.body(Mono.just(bankAccount), BankAccount.class)
				.retrieve()
				.bodyToMono(Response.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = factory.create("services");
					return rcb.run(it, throwable -> Mono.just(new Response()));
				});
	}

	@Override
	public Flux<Transaction> listTransactionClientReact(String codeClient, String codeTransaction) {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_GATEWAY)
				.get()
				.uri(Constants.PATH_SERVICE_TRANSACTION_URI+codeClient+"/"+codeTransaction)
				.retrieve()
				.bodyToFlux(Transaction.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = factory.create("services");
					return rcb.run(it, throwable -> Flux.just(new Transaction()));
				});
	}

	@Override
	public Flux<Transaction> findAllTransaction() {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_GATEWAY)
				.get()
				.uri(Constants.PATH_SERVICE_TRANSACTION_URI)
				.retrieve()
				.bodyToFlux(Transaction.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = factory.create("services");
					return rcb.run(it, throwable -> Flux.just(new Transaction()));
				});
	}

	@Override
	public  Mono<Response> saveTransaction(Mono<Transaction> transaction) {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_GATEWAY)
				.post()
				.uri(Constants.PATH_SERVICE_TRANSACTION_URI)
				.body(transaction,Transaction.class)
				.retrieve()
				.bodyToMono(Response.class )
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = factory.create("services");
					return rcb.run(it, throwable -> Mono.just(new Response()));
				});
	}
}
