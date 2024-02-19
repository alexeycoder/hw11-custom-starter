package edu.alexey.spring.library.issueservice.dto;

import java.util.UUID;

public record AuthorDto(
		UUID authorId,
		String firstName,
		String lastName) {
}
