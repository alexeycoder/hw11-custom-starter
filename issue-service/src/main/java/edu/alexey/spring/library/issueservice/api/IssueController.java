package edu.alexey.spring.library.issueservice.api;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.library.entities.Issue;
import edu.alexey.spring.library.issueservice.dto.BookDto;
import edu.alexey.spring.library.issueservice.dto.IssueDescriptionDto;
import edu.alexey.spring.library.issueservice.dto.ReaderDto;
import edu.alexey.spring.library.issueservice.services.BookProvider;
import edu.alexey.spring.library.issueservice.services.ReaderProvider;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

	private final BookProvider bookProvider;
	private final ReaderProvider readerProvider;
	private final Faker faker;
	private List<Issue> issues;

	public IssueController(BookProvider bookProvider, ReaderProvider readerProvider) {
		this.bookProvider = bookProvider;
		this.readerProvider = readerProvider;
		this.faker = new Faker();
	}

	@PostConstruct
	void init() {
		this.issues = Stream.<Issue>generate(() -> {
			Issue issue = new Issue();
			issue.setIssueId(UUID.randomUUID());
			issue.setIssuedAt(faker.date()
					.past(9999, 1, TimeUnit.DAYS)
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate());
			issue.setBookId(bookProvider.getRandomBookId());
			issue.setReaderId(readerProvider.getRandomReaderId());
			return issue;
		}).limit(15).toList();
	}

	@GetMapping
	public List<Issue> getAll() {
		return issues;
	}

	@GetMapping("/random")
	public ResponseEntity<IssueDescriptionDto> getRandom() {
		int randomIndex = faker.number().numberBetween(0, issues.size());

		Issue issue = issues.get(randomIndex);
		BookDto bookDto = bookProvider.findBookById(issue.getBookId());
		ReaderDto readerDto = readerProvider.findReaderById(issue.getReaderId());
		IssueDescriptionDto descriptionDto = new IssueDescriptionDto(
				issue.getIssueId(),
				issue.getIssuedAt(),
				bookDto,
				readerDto);

		return ResponseEntity.ok(descriptionDto);
	}

}
