package edu.alexey.spring.library.issueservice.dto;

import java.time.LocalDate;
import java.util.UUID;

public record IssueDescriptionDto(
		UUID issueId,
		LocalDate issuedAt,
		BookDto book,
		ReaderDto reader) {
}
