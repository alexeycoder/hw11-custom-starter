package edu.alexey.spring.library.issueservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import edu.alexey.spring.library.issueservice.dto.ReaderDto;
import io.netty.util.internal.ThreadLocalRandom;

@Service
public class ReaderProvider {

	private final WebClient webClient;
	private final EurekaClient eurekaClient;

	public ReaderProvider(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
		this.webClient = WebClient.builder().build();
	}

	public UUID getRandomReaderId() {
		// GET reader-service:8180/api/readers/random -> UUID
		// webClient.get().uri("http://localhost:8180/api/readers/random")

		ReaderDto readerResponse = webClient.get()
				.uri(getReaderServiceIp().concat("/api/readers/random"))
				.retrieve()
				.bodyToMono(ReaderDto.class)
				.block();

		return readerResponse.readerId();
	}

	public ReaderDto findReaderById(UUID readerId) {

		ReaderDto readerResponse = webClient.get().uri(getReaderServiceIp().concat("/api/readers/" + readerId))
				.retrieve()
				.bodyToMono(ReaderDto.class)
				.block();

		return readerResponse;
	}

	private String getReaderServiceIp() {
		Application application = eurekaClient.getApplication("READER-SERVICE");
		List<InstanceInfo> instances = application.getInstances();
		int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
		InstanceInfo randomInstance = instances.get(randomIndex);
		return randomInstance.getHomePageUrl();
	}
}
