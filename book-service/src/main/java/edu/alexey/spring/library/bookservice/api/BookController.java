package edu.alexey.spring.library.bookservice.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.library.entities.Author;
import edu.alexey.spring.library.entities.Book;

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
			book.setTitle(faker.book().title());

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

	@GetMapping("/{uuid}")
	public ResponseEntity<Book> getRandom(@PathVariable UUID uuid) {

		Optional<Book> bookOpt = books.stream().filter(b -> b.getBookId().equals(uuid)).findAny();
		return ResponseEntity.of(bookOpt);
	}
}
