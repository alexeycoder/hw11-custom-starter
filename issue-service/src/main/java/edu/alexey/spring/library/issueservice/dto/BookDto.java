package edu.alexey.spring.library.issueservice.dto;

import java.util.UUID;

public record BookDto(
		UUID bookId,
		String title,
		AuthorDto author) {
}
