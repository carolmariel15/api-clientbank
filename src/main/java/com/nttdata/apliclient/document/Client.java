package com.nttdata.apliclient.document;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Getter
//@Setter
//@NoArgsConstructor
@Document(collection = "clients")
//@Data
public class Client extends Person {

	@Id
	private String id;
	private String corporation;
	private TypeClient typeClient;
	private List<Person> holders;
	private List<Person> signatory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public TypeClient getTypeClient() {
		return typeClient;
	}

	public void setTypeClient(TypeClient typeClient) {
		this.typeClient = typeClient;
	}

	public List<Person> getHolders() {
		return holders;
	}

	public void setHolders(List<Person> holders) {
		this.holders = holders;
	}

	public List<Person> getSignatory() {
		return signatory;
	}

	public void setSignatory(List<Person> signatory) {
		this.signatory = signatory;
	}

	public Client() {
		super();
	}
	
	
}
