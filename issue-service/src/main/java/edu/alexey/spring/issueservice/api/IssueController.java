package edu.alexey.spring.issueservice.api;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import edu.alexey.spring.issueservice.entities.Issue;
import edu.alexey.spring.issueservice.services.BookProvider;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

	private final BookProvider bookProvider;
	private List<Issue> issues;
	private final Faker faker;

	public IssueController(BookProvider bookProvider) {
		this.bookProvider = bookProvider;
		this.faker = new Faker();
	}

	@GetMapping
	public List<Issue> getAll() {
		return issues;
	}

	@GetMapping("/random")
	public Issue getRandom() {
		int randomIndex = faker.number().numberBetween(0, issues.size());
		return issues.get(randomIndex);
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
			issue.setReaderId(UUID.randomUUID());
			return issue;
		}).limit(15).toList();
	}
}
