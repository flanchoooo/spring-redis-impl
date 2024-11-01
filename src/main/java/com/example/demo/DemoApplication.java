package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableScheduling
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(HotRepository hotRepository) {
		return args -> {
			System.out.println("Application started and ready to query the database...");
		};
	}

	@Component
	public static class ScheduledTasks {

		@Autowired
		private HotRepository hotRepository;

		@Autowired
		private StringRedisTemplate redisTemplate;

		// Cache to store unique combinations of tager and amount to prevent duplication
//		private final Cache<String, Boolean> requestCache = CacheBuilder.newBuilder()
//				.expireAfterWrite(10, TimeUnit.SECONDS)
//				.build();

		// Run every 5 seconds
//		@Scheduled(fixedRate = 5000)
//		public void queryDatabaseEveryFiveSeconds() {
//			List<Hot> hotEntries = hotRepository.findAll();
//			System.out.println("Queried 'hot' table at " + System.currentTimeMillis());
//
//			hotEntries.forEach(entry -> {
//				// Generate a unique key based on tager and amount
//				String key = entry.getTager() + ":" + entry.getAmount();
//				// Check if the key exists in the cache
//				if (requestCache.getIfPresent(key) == null) {
//					// Key not present, process entry and add to cache
//
//					System.out.println("Original request for Tager: " + "ID: " + entry.getId() + ", Tager: " + entry.getTager() + ", Amount: " + entry.getAmount());
//					requestCache.put(key, true); // Add key to cache to prevent duplication
//				} else {
//					// Key already in cache, print duplication message
//					entry.setStatus("DUPLICATE REQUEST | ALREADY PROCESSED"); // Example of incrementing amount
//					hotRepository.save(entry);
//					System.out.println("Duplicate detected for Tager: " + entry.getTager() + ", Amount: " + entry.getAmount());
//				}
//			});
//		}


		@Scheduled(fixedRate = 10)
		public void queryDatabaseEveryFiveSeconds() {
			List<Hot> hotEntries = hotRepository.findAll();
			//System.out.println("Queried 'hot' table at " + System.currentTimeMillis());

			ValueOperations<String, String> operations = redisTemplate.opsForValue();
			hotEntries.forEach(entry -> {
				String key = "hot:" + entry.getTager() + ":" + entry.getAmount();
				if (operations.get(key) == null) {
					// Process new entry
					log.info("Original Request: " + entry.getId() + ", Tager: " + entry.getTager() + ", Amount: " + entry.getAmount());
					operations.set(key, "processed", 10, TimeUnit.SECONDS);
				} else {
					// Update entry if duplicate
					log.info("Duplicate request detected for Tager: " + entry.getId() + ", Tager: " + entry.getTager() + ", Amount: " + entry.getAmount());
					entry.setStatus("Already Processed"); // Example of incrementing amount
					hotRepository.save(entry); // Update database
				}
			});
		}
	}

}
