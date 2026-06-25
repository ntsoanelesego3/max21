# Project Update Log

## 2026-06-25

### Analysis Summary

This workspace contains four Spring Boot services:

- `server-register`: Eureka server.
- `api-gatewayV2`: Spring Cloud Gateway client.
- `course-service`: MongoDB-backed course service.
- `studnet-service`: PostgreSQL-backed student service.

The project structure was kept the same. Existing module/package names were not renamed, including `studnet-service`, to avoid a broad refactor.

### Batch 1 Changes

- Changed all module `java.version` values to `21`, matching the installed Maven runtime JDK.
- Removed the JPA test starter from `course-service` because it is a MongoDB service and the JPA test starter caused Spring Boot to auto-configure a SQL `DataSource`.
- Changed `course-service` Mongo property from `spring.mongodb.uri` to `spring.data.mongodb.uri`.
- Changed student ID usage from `int` / `Integer` to `Long` in controller, service, and repository code to match `Student.id`.
- Fixed the Feign client routes in `studnet-service` so they call the `course-service` controller under `/course`.

### Batch 2 Changes

- **Reverted MongoDB URI Property in `course-service`**: Changed the MongoDB property back to `spring.mongodb.uri` (from `spring.data.mongodb.uri` introduced in Batch 1). In Spring Boot 4.0, `spring.mongodb.uri` is the correct key; using `spring.data.mongodb.uri` caused it to fall back to `localhost:27017` and fail to connect to the configured Atlas cluster.
- **Fixed Feign Client Signature Inconsistency in `studnet-service`**: Updated the Feign client `studentInterface.getCourseByLanguage` return type from `CourseResponse` to `List<CourseResponse>` to match the `List<Course>` response signature returned by the `CourseController` in `course-service`.
- **Propagated Correct Feign Client Type**: Updated `StudnetService` and `StudentController` in `studnet-service` to correctly handle and return `List<CourseResponse>` for the `getCourseByLanguage` endpoint.
- **Fixed Potential NullPointerException in `course-service`**: In `CourseService.findByTitle()`, removed redundant and unsafe getter calls (`course.getDescription()`, etc.) that would throw a `NullPointerException` if a course was not found by title.
- **Cleaned Up Redundant Optional Wrapping**: Simplified convoluted `Optional.ofNullable(...).orElseThrow(...).get()` logic in `StudnetService.updateStudent()` to a direct `repo.findById(id).orElseThrow(...)`.

### Verification Notes

- Before Batch 1, Maven failed normally because the POMs requested Java 25/26 while Maven was running on JDK 21.
- With Batch 1 fixes, tests for `studnet-service`, `server-register`, and `api-gatewayV2` compiled and passed.
- With Batch 2 fixes, `course-service` successfully connects to the remote Atlas MongoDB cluster using `spring.mongodb.uri` (instead of failing to connect to `localhost:27017`), and all tests across all four services pass successfully (`BUILD SUCCESS`).
- Verified code compile-time and runtime correctness for Feign client mapping and NPE prevention.

### Batch 3 Changes

- **Resolved Gateway 404 Errors (WebMVC Router Config)**: Identified that `api-gatewayV2` uses the servlet-based **Spring Cloud Gateway MVC** (`spring-cloud-starter-gateway-server-webmvc`). In this version, the standard WebFlux discovery locator properties (`spring.cloud.gateway.discovery.locator.enabled`) are not supported, causing incoming requests to return a **404 Not Found**.
- **Configured WebMVC Routes**: Added WebMVC-compliant routes under `spring.cloud.gateway.mvc.routes` namespace in `api-gatewayV2`'s [application.properties](file:///C:/Users/Lesego/IdeaProjects/course/api-gatewayV2/src/main/resources/application.properties) pointing to load-balanced instances (`lb://course-service` and `lb://studnet-service`) with `StripPrefix=1` filter.
- **Added Explicit Eureka Default Zone**: Configured `eureka.client.service-url.defaultZone=http://localhost:8761/eureka` and `prefer-ip-address=true` in `api-gatewayV2` and `course-service` properties files to ensure consistent Eureka service discovery resolution.

### Verification Notes

- All 4 modules now compile, pass tests, and are properly configured with WebMVC routing, database connectivity, and Feign client parameters.
- Postman requests via the Gateway are now fully routed to the respective services.

