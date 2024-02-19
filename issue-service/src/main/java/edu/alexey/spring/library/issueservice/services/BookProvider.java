package edu.alexey.spring.library.issueservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import edu.alexey.spring.library.issueservice.dto.BookDto;
import io.netty.util.internal.ThreadLocalRandom;

@Service
public class BookProvider {

	// HttpClient	- java.net
	// RestTemplate	- spring.web
	// WebClient	- spring.reactive

	private final WebClient webClient;
	private final EurekaClient eurekaClient;

	public BookProvider(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
		this.webClient = WebClient.builder().build();
	}

	public UUID getRandomBookId() {
		// GET book-service:8180/api/books/random -> UUID
		// webClient.get().uri("http://localhost:8180/api/books/random")

		BookDto bookResponse = webClient.get()
				.uri(getBookServiceIp().concat("/api/books/random"))
				.retrieve()
				.bodyToMono(BookDto.class)
				//.subscribe(resp -> { /* response received callback */ })
				.block();

		return bookResponse.bookId();
	}

	public BookDto findBookById(UUID bookId) {

		BookDto bookResponse = webClient.get().uri(getBookServiceIp().concat("/api/books/" + bookId))
				.retrieve()
				.bodyToMono(BookDto.class)
				.block();

		return bookResponse;
	}

	private String getBookServiceIp() {
		Application application = eurekaClient.getApplication("BOOK-SERVICE");
		List<InstanceInfo> instances = application.getInstances();
		int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
		InstanceInfo randomInstance = instances.get(randomIndex);
		return randomInstance.getHomePageUrl();
	}
}
