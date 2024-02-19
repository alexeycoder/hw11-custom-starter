package edu.alexey.spring.library.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Book {

	private UUID bookId;
	private String title;
	private Author author;
}
