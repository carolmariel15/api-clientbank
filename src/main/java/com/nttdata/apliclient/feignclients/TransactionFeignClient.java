package com.nttdata.apliclient.feignclients;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FeignClient(name="api-transaction",url="http://localhost:8085")
@RequestMapping("/api/transaction")
public interface TransactionFeignClient {
	
	@GetMapping("/client/{codeClient}/{codeTransaction}")
	public List<Transaction> listTransactionClient(@PathVariable("codeClient") String codeClient,@PathVariable("codeTransaction") String codeTransaction);
	
	@PostMapping
	public Map<String, Object> saveTransaction(@Valid @RequestBody Transaction monoTransaction);

}
