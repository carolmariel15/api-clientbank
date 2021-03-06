package com.nttdata.apliclient.controller;


import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.models.*;
import com.nttdata.apliclient.service.IClientService;
import com.nttdata.apliclient.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private IClientService service;

	
	
	private static final Logger LOGGER = LogManager.getLogger(ClientController.class);

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> findAll() {
    	LOGGER.info("metodo listarCliente: lista todos los clientes");
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> findById(@PathVariable String id) {
    	LOGGER.info("metodo findById: muestra un cliente por id");
        return service.findById(id).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    

    @GetMapping("/code/{code}")
    public Mono<ResponseEntity<Client>> findByCode(@PathVariable String code) {
    	LOGGER.info("metodo findByCode: muestra un cliente por codigo de cliente");
        return service.findByCodeClient(code).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/holders/{dni}")
    public Mono<ResponseEntity<Client>> findByHoldersDni(@PathVariable String dni) {
    	LOGGER.info("metodo findByHoldersDni: muestra un cliente por codigo de representante");
        return service.findByHoldersDni(dni).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/crit/{dni}-{phone}")
    public Mono<ResponseEntity<Client>> findByHoldersDniAndHoldersPhone(@PathVariable String dni,@PathVariable String phone) {
    	LOGGER.info("metodo findByHoldersDniAndHoldersPhone: muestra un cliente mor dni y telefono representante");
        return service.findByHoldersDniAndHoldersPhone(dni,phone).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }



    @PostMapping
   public Mono<ResponseEntity<Map<String, Object>>> save(@Valid @RequestBody Mono<Client> monoClient) {
        Map<String, Object> respuesta = new HashMap<>();
        LOGGER.info("metodo saveClient: guarda los datos del cliente y envia la respuesta exitosa, caso contrario se envia una respuesta erronia");

        return monoClient.flatMap(client -> {
        	if (client.getTypeClient().getId()==Constants.TIPO_CLIENT_NATURAL) {
        		client.setDni(client.getCodeClient());
        	}else {
        		client.setRuc(client.getCodeClient());
        	}
        		
            return service.save(client).map(c-> {
                respuesta.put("Client", c);
                respuesta.put("mensaje", "Cliente guardado con exito");
                respuesta.put("timestamp", new Date());

                return ResponseEntity.created(URI.create("/api/client/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            }).doOnSuccess(e->LOGGER.info("Todo OK"));
        }).onErrorResume(t -> {
        	
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux:: fromIterable)
                    .map(fieldError -> "El campo: " + fieldError.getField() + " " +fieldError.getDefaultMessage())
                    .doOnError(e->LOGGER.error(e.getMessage()))
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
    public Mono<ResponseEntity<Client>> update(@RequestBody Client client, @PathVariable String id) {
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
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    	LOGGER.info("metodo deleteClient: elimina el cliente");
    	
        return service.findById(id).flatMap(c -> {
            return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    
	/*@GetMapping("/trasaction/{codeClient}/{codeTransaction}")
	public Mono<ResponseEntity<Flux<Transaction>>>  listTransactionClient(@PathVariable("codeClient") String codeClient,@PathVariable("codeTransaction") String codeTransaction) {
		LOGGER.info("metodo listTransactionClient: metodo de comunicacion al servicio name api-transaction");
				
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				serviceTransaction.listTransactionClient(codeClient,codeTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());
		     }*/
		
	
	/*@GetMapping("/trasaction/findAll")
	public Mono<ResponseEntity<Mono<Transaction>>> findAllTransaction() {
		LOGGER.info("metodo listarTransaction");
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(serviceTransaction.findAll()));
	}*/
	
	/*@PostMapping("/trasaction")
	public Mono<ResponseEntity<Mono<Response>>>  saveTransaction(@Valid @RequestBody Mono<Transaction> monoTransaction) {
	
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				serviceTransaction.save(monoTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());

	}*/


    @GetMapping("/report/{codeClient}")
    public Mono<ResponseEntity<Mono<ClientProducts>>>  listTransactionClient(@PathVariable("codeClient") String codeClient) {


        return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                service.findByCodeClientProducts(codeClient))).defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/report/{codeClient}/{typeAccount}/{period}")
    public Mono<ResponseEntity<Mono<ClientReports>>>  findByReportGeneralClient(@PathVariable("codeClient") String codeClient,
                                                                                @PathVariable("typeAccount") Integer typeAccount,
                                                                                @PathVariable("period") String period
    ){
        LOGGER.info("metodo listTransactionClient: metodo de comunicacion al servicio name api-transaction");

        return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                service.findByReportGeneralClient(codeClient,typeAccount,period))).defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PutMapping("/edit/{codeClient}/{accountNumber}")
    public  Mono<ResponseEntity<BankAccount>> editCard(@RequestBody Card card, @PathVariable String codeClient, @PathVariable String accountNumber) {

        //String codeClient, String accountNumber
        return service
                .editCard(card,codeClient,accountNumber)
                .map(ba -> ResponseEntity.created(URI.create("/client/".concat(codeClient).concat("/").concat(accountNumber)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(ba))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }


    @GetMapping("/transaction/report/{codeClient}/{typeAccount}/{numberCard}")
    public Mono<ResponseEntity<Flux<Transaction>>> listTransactionClientReport(@PathVariable("codeClient") String codeClient,
                                                                               @PathVariable("typeAccount") Integer typeAccount,
                                                                               @PathVariable("numberCard") String numberCard
    ) {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                service.reportTransactionLimit(codeClient, typeAccount,numberCard))).defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
