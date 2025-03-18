# Weather Caching Service

## Overview
This project is a **Spring Boot-based Weather Caching Service** that provides weather information for different cities, with caching mechanisms to improve performance. The service interacts with a database and leverages **Spring Cache** to optimize repeated weather data requests.

## Features
- Fetch weather data by city with caching.
- Inspect cache contents for debugging purposes.
- Add new weather records.
- Update existing weather records and refresh the cache.
- Delete weather records and evict cache entries.

## Technologies Used
- **Spring Boot** - Backend framework
- **Spring Cache (EhCache or Caffeine)** - Caching mechanism
- **Spring Data JPA** - Database interaction
- **H2/PostgreSQL/MySQL** - Database
- **Spring Boot REST API** - Exposes endpoints

## Installation & Setup

### Prerequisites
- Java 17+
- Maven
- Database (H2, MySQL, PostgreSQL)

### Clone the Repository
```sh
 git clone https://github.com/your-repo/weather-caching-service.git
 cd weather-caching-service
```

### Build and Run the Application
```sh
 mvn clean install
 mvn spring-boot:run
```

## API Endpoints

### 1. Fetch Weather Data
**GET /weather?city={cityName}**
- Retrieves weather information from the cache or database.

### 2. Inspect Cache
**GET /weather/inspect**
- Prints cache details to the console.

### 3. Add New Weather Record
**POST /weather**
- Adds a new weather record.
- Request Body:
  ```json
  {
    "city": "New York",
    "forecast": "Sunny"
  }
  ```

### 4. Get All Weather Records
**GET /weather/all**
- Fetches all stored weather records.

### 5. Update Weather Data
**PUT /weather/{city}?weatherUpdate={newForecast}**
- Updates weather information and refreshes cache.

### 6. Delete Weather Data
**DELETE /weather/{city}**
- Deletes weather data for a city and evicts the cache entry.

## Caching Implementation
- **@Cacheable(value = "weather", key = "#city")** - Caches fetched weather data.
- **@CachePut(value = "weather", key = "#city")** - Updates the cache when weather data is modified.
- **@CacheEvict(value = "weather", key = "#city")** - Removes cache entry when weather data is deleted.

## Cache Inspection
- **CacheInspectionService** provides a method to inspect and print the cache contents.

## Contributing
1. Fork the repository.
2. Create a feature branch.
3. Commit changes and push.
4. Create a pull request.

## License
This project is licensed under the MIT License.

## Contact
For any queries, reach out to **your-email@example.com**.

