package com.nttdata.apliclient.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clients")
public class Client extends Person {

	@Id
	private String id;
	private String codeClient;
	private String ruc;
	private String corporation;

	
	private TypeClient typeClient;
	private List<Person> holders;
	private List<Person> signatory;

	
}
