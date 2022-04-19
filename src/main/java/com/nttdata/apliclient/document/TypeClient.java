package com.nttdata.apliclient.document;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TypeClient {
	
	@Id
	private String id;
    
	private String type;
	

	

}
