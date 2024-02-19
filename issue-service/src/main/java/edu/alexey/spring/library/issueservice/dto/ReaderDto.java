package edu.alexey.spring.library.issueservice.dto;

import java.util.UUID;

public record ReaderDto(
		UUID readerId,
		String firstName,
		String lastName) {
}
