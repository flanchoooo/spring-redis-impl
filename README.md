# Deduplication Service

This Java Spring application is designed to handle deduplication of incoming data using Redis as a cache layer. It prevents duplicate records from being processed by storing unique identifiers in Redis and checking for duplicates before processing each request.

## Features
- **Deduplication**: Efficiently detect and skip duplicate records using Redis.
- **High Performance**: Leveraging Redis for fast read/write operations ensures optimal performance for high-throughput scenarios.
- **Extensibility**: Built with a modular architecture for easy extension and customization.

## Technologies Used
- **Java**: Programming language.
- **Spring Boot**: Framework for building Java-based applications.
- **Spring Data Redis**: Integration with Redis for caching and data storage.
- **Redis**: In-memory data structure store used for caching and deduplication.

