package edu.alexey.spring.readerservice.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Author {

	private UUID authorId;
	private String firstName;
	private String lastName;
}
