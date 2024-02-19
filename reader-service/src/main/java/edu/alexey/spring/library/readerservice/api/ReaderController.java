package edu.alexey.spring.library.readerservice.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.someother.executiontimer.annotations.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.library.entities.Reader;

@RestController
@RequestMapping("/api/readers")
@Timer
public class ReaderController {

	private final List<Reader> readers;
	private final Faker faker;

	public ReaderController() {
		this.faker = new Faker();
		this.readers = Stream.<Reader>generate(() -> {
			Reader reader = new Reader();
			reader.setReaderId(UUID.randomUUID());
			reader.setFirstName(faker.name().firstName());
			reader.setLastName(faker.name().lastName());
			return reader;
		}).limit(15).toList();
	}

	@GetMapping
	public List<Reader> getAll() {
		return readers;
	}

	@GetMapping("/random")
	public Reader getRandom() {
		int randomIndex = faker.number().numberBetween(0, readers.size());
		return readers.get(randomIndex);
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<Reader> getRandom(@PathVariable UUID uuid) {

		Optional<Reader> readerOpt = readers.stream().filter(r -> r.getReaderId().equals(uuid)).findAny();
		return ResponseEntity.of(readerOpt);
	}
}
