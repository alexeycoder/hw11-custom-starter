package edu.alexey.spring.readerservice.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.readerservice.entities.Author;
import edu.alexey.spring.readerservice.entities.Reader;

@RestController
@RequestMapping("/api/books")
public class ReaderController {

	private final List<Reader> books;
	private final Faker faker;

	public ReaderController() {
		this.faker = new Faker();
		this.books = Stream.<Reader>generate(() -> {
			Reader book = new Reader();
			book.setBookId(UUID.randomUUID());
			book.setName(faker.book().title());

			Author author = new Author();
			author.setAuthorId(UUID.randomUUID());
			author.setFirstName(faker.name().firstName());
			author.setLastName(faker.name().lastName());

			book.setAuthor(author);
			return book;
		}).limit(15).toList();
	}

	@GetMapping
	public List<Reader> getAll() {
		return books;
	}

	@GetMapping("/random")
	public Reader getRandom() {
		int randomIndex = faker.number().numberBetween(0, books.size());
		return books.get(randomIndex);
	}
}
