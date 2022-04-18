package com.nttdata.apliclient.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.apliclient.document.*;

public interface IClientDao extends ReactiveMongoRepository<Client, String>{

}
