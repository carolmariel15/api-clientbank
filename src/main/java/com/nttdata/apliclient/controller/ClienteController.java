package com.nttdata.apliclient.controller;


import java.net.URI;
import java.util.Date;
import java.util.HashMap;
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
import com.nttdata.apliclient.model.Client;
import com.nttdata.apliclient.service.IClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	
	@Autowired
	private IClientService service;
	

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> listarCliente() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> verDetallesCliente(@PathVariable String id) {
        return service.findById(id).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
   public Mono<ResponseEntity<Map<String, Object>>> guardarCliente(@Valid @RequestBody Mono<Client> monoCliente) {
        Map<String, Object> respuesta = new HashMap<>();

        return monoCliente.flatMap(cliente -> {
            return service.save(cliente).map(c-> {
                respuesta.put("cliente", c);
                respuesta.put("mensaje", "Cliente guardado con exito");
                respuesta.put("timestamp", new Date());

                return ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });
        }).onErrorResume(t -> {
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
    public Mono<ResponseEntity<Client>> editarCliente(@RequestBody Client cliente, @PathVariable String id) {
        return service.findById(id).flatMap(c -> {
            c.setDirection(cliente.getDirection());
              return service.save(c);
        }).map(c -> ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarCliente(@PathVariable String id) {
        return service.findById(id).flatMap(c -> {
            return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
	 
}
