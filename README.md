# Project Overview

This project is organized in a clear modular structure to keep the code simple, readable, and easy to maintain.

## Module Structure

- **Presentation** – Handles the user interface and connects it to the application logic.
- **Service** – Contains the main business logic and coordinates workflows.
- **Repository** – Manages database operations and persistence.
- **Entity** – Defines the core data models.
- **DTO** – Transfers data between layers safely and efficiently.
- **Validation** – Validates inputs and enforces rules.
- **Exception** – Defines custom exceptions for meaningful error handling.
- **Session** – Manages user session and application state.
- **Util** – Provides helper functions and utilities.
- **Enum** – Contains predefined constant sets.

## Design Notes

- Keep the UI thin and delegate logic to services.
- Use entities to represent business rules and data.
- Services should stay focused and cohesive.
- Repositories handle persistence only.
- Validators protect against invalid data.
- Utilities should stay simple and stateless.

## Opening JavaDoc

You can open the generated JavaDoc here:  
[Open JavaDoc](https://besho-122.github.io/Doc/)

The documentation is located in the `docs/` folder
