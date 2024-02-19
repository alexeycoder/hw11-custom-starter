package edu.alexey.spring.library.entities;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class Issue {

	private UUID issueId;
	private LocalDate issuedAt;
	private UUID bookId;
	private UUID readerId;
}
