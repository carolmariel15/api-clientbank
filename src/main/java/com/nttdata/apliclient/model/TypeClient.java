package com.nttdata.apliclient.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;

/*@Getter
@Setter
@NoArgsConstructor*/


public class TypeClient {
	
	@Id
	private String id;
    
	/*@NotEmpty*/
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TypeClient(String type) {
		super();
		this.type = type;
	}

	public TypeClient() {
		super();
	}
	
	

}
