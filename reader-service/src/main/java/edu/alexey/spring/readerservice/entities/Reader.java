package edu.alexey.spring.readerservice.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Reader {

	private UUID bookId;
	private String name;
	private Author author;
}
