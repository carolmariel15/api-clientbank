package com.nttdata.apliclient.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	private String dni;
	private String firstName;
	private String lastName;
	private String direction;
	private String phone;
	private String email;




}
