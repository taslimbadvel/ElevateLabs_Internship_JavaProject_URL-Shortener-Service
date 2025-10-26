## URL Shortener

A simple Spring Boot application that shortens long URLs into short, shareable links — similar to Bitly or TinyURL.  
Built with Java, Spring Boot, and H2 in-memory database.

---

### Project Structure"

        src
        └── main
        ├── java
        │   └── com.example.urlshortener
        │       ├── controller
        │       │    └── UrlController.java        # REST endpoints
        │       ├── model
        │       │    └── UrlMapping.java           # Entity class
        │       ├── repository
        │       │    └── UrlMappingRepository.java # JPA repository
        │       ├── service
        │       │    └── UrlService.java           # Business logic
        │       └── util
        │            └── Base62.java               # Base62 encoding utility
        └── resources
        ├── application.properties             # Configuration file
                     
        

---

## Tech Stack

    Java 17+
    Spring Boot 3
    Spring Data JPA
    H2 Database (in-memory)
    Maven

## Tools
    Postman (for API testing)
    Swagger (Springdoc OpenAPI 3)
    IntelliJ IDEA (IDE)
    Git & GitHub (version control)


---

##  Features

     Shorten any valid long URL  
     Redirect short URL to original link  
     Swagger UI for easy API testing
     Track number of clicks per link  
     Retrieve statistics for each short link  
     List all shortened URLs (for testing/development)

---

##  API Endpoints

| Method  | Endpoint               | Description                |
|---------|------------------------|----------------------------|
| POST    | `/api/v1/shorten`      | Shorten a new long URL     |
| GET     | `api/v1/{code}`        | Redirect to original URL   |
| GET     | `/api/v1/{code}/stats` | View stats for a short URL |
| GET     | `/api/v1/links`        | List all shortened URLs    |

---

## Example Usage
**Postman Guide**(option1)

**1. Shorten a URL**

**Request**  
   **(Post Method):** http://localhost:8080/api/v1/shorten

   **json:**
            
                {
                    "longUrl": "https://www.google.com/search?q=spring+boot+url+shortener"
                }

**Response**
**json**

                 {
                    "shortUrl": "http://localhost:8080/1"
                 }          


**Swagger UI**(option2)

The project includes Swagger (Springdoc OpenAPI) for easy browser-based testing.

After running the project, open:
    
http://localhost:8080/swagger-ui/index.html
    
You’ll see all API endpoints with the option to test them directly.
    
---


## Run Locally

    Prerequisites:
    
    Java 17+
    
    Maven installed

**Steps**
# Clone the repository
  git clone https://github.com/taslimbadvel/ElevateLabs_Internship_JavaProject_URL-Shortener-Service.git
        
# Navigate into project
  cd urlshortener
    
# Run the application
  mvn spring-boot:run

----

## Database

This project uses the H2 in-memory database.

All data is temporary and will be lost when the application restarts.

You can access the H2 Console at:
👉 http://localhost:8080/h2-console

**JDBC URL** (based on `application.properties`):
- In-memory: `jdbc:h2:mem:urlshortener`
- File (persistent): `jdbc:h2:file:./data/urlshortener`  
User: `sa`  
Password: *(leave empty)*

**Result:**
        
        SELECT * FROM URL_MAPPING;

        ID  	CLICKS  	CREATED_AT  	LONG_URL  	SHORT_CODE  
        1	1	2025-10-25 12:29:34.108863+00	https://www.wikipedia.org/	1
        2	1	2025-10-25 12:46:50.789088+00	https://www.google.com	2
        3	0	2025-10-25 13:15:35.835375+00	https://www.github.com/spring-projects/spring-boot	3
        (3 rows, 3 ms)


**Application runs on:**
 http://localhost:8080