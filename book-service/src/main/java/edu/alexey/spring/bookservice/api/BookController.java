package edu.alexey.spring.bookservice.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.bookservice.entities.Author;
import edu.alexey.spring.bookservice.entities.Book;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final List<Book> books;
	private final Faker faker;

	public BookController() {
		this.faker = new Faker();
		this.books = Stream.<Book>generate(() -> {
			Book book = new Book();
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
	public List<Book> getAll() {
		return books;
	}

	@GetMapping("/random")
	public Book getRandom() {
		int randomIndex = faker.number().numberBetween(0, books.size());
		return books.get(randomIndex);
	}
}
