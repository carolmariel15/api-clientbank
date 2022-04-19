package com.nttdata.apliclient.controller;


import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.service.IClientService;

import models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private IClientService service;
	
	private static final Logger LOGGER = LogManager.getLogger(ClientController.class);

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> listClient() {
    	LOGGER.info("metodo listarCliente: lista todos los clientes");
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> oneClient(@PathVariable String id) {
    	LOGGER.info("metodo oneClient: muestra un cliente por id");
        return service.findById(id).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
   public Mono<ResponseEntity<Map<String, Object>>> saveClient(@Valid @RequestBody Mono<Client> monoClient) {
        Map<String, Object> respuesta = new HashMap<>();
        LOGGER.info("metodo saveClient: guarda los datos del cliente y envia la respuesta exitosa, caso contrario se envia una respuesta erronia");

        return monoClient.flatMap(client -> {
            return service.save(client).map(c-> {
                respuesta.put("cliente", c);
                respuesta.put("mensaje", "Cliente guardado con exito");
                respuesta.put("timestamp", new Date());

                return ResponseEntity.created(URI.create("/api/client/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });
        }).onErrorResume(t -> {
        	
        	LOGGER.error("Se ha produccido un error se envia");
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux:: fromIterable)
                    .map(fieldError -> "El campo: " + fieldError.getField() + " " +fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());

                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }

   @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> editClient(@RequestBody Client client, @PathVariable String id) {
	   LOGGER.info("metodo editClient: guarda los datos del cliente y envia la respuesta exitosa, caso contrario se envia una respuesta erronia");
        return service.findById(id).flatMap(c -> {
            c.setDirection(client.getDirection());
              return service.save(c);
        }).map(c -> ResponseEntity.created(URI.create("/api/client/".concat(c.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable String id) {
    	LOGGER.info("metodo deleteClient: elimina el cliente");
    	
        return service.findById(id).flatMap(c -> {
            return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    
	@GetMapping("/trasaction/{codeClient}/{codeTransaction}")
	public Mono<ResponseEntity<Flux<Transaction>>>  listTransactionClient(@PathVariable("codeClient") String codeClient,@PathVariable("codeTransaction") String codeTransaction) {
		LOGGER.info("metodo listTransactionClient: metodo de comunicacion al servicio name api-transaction");
				
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				service.listTransactionClient(codeClient,codeTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());
		     }   
    
	 
}