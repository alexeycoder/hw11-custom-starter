package edu.alexey.spring.library.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Reader {

	private UUID readerId;
	private String firstName;
	private String lastName;
}
