package edu.alexey.spring.bookservice.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Book {

	private UUID bookId;
	private String name;
	private Author author;
}
