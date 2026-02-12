# RBS — Bezbedna turistička agencija

**RBS Projekat 2025/2026** — “Putuj bezbedno”

This repository contains a (demo) web application for a tourist agency with an emphasis on *secure* development practices and common web vulnerabilities (e.g., SQLi/XSS/CSRF) in a controlled learning environment.

---
## Changelog
| Version | Date        | Summary                                                                                                                                    |
|---------|-------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| 0.1.0   | 09.02.2026. | Initial version.                                                                                                                           |
| 0.1.1   | 12.02.2026. | Fixed the INSERT INTO statement in the `create` method in the CityRepository class. This chagne **does not** affect the SQLi and XSS task. |

---
## Tech stack

- Java 8
- Spring Boot (MVC)
- Thymeleaf templates
- H2 database (in-memory or file)
- Maven Wrapper (`mvnw`, `.mvn/`)
- Node.js for frontend tooling
- Git
---

## Features (current direction)

- Countries
- Cities (linked to country)
- Hotels (linked to city)
- Room types per hotel (capacity, price per night, total rooms)

### Booking
- Create reservations:
  - choose room type
  - select start/end dates
  - number of rooms and guests
- View:
  - “My reservations”
  - “All reservations”
- Delete reservation (optional / role-based)

## Project structure

At repo root you’ll find:
- `src/` (application code)
- `pom.xml` (Maven config)
- `mvnw`, `mvnw.cmd`, `.mvn/`
- `csrf-exploit/` (CSRF demo)
---
