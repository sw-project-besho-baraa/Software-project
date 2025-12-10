# Project Documentation Overview

This project follows a layered modular structure with clear separation of concerns.

## Module Structure

- Presentation: UI/controllers that translate user intent into service calls.
- Service: Business use-cases, orchestration and transaction boundaries.
- Repository: Data access and persistence abstraction.
- Entity: Domain entities representing core business concepts.
- DTO: Data Transfer Objects for moving data across layers.
- Validation: Reusable validators enforcing input and domain constraints.
- Exception: Custom exceptions to signal domain-specific error conditions.
- Session: Session and contextual state management.
- Util: Cross-cutting utilities and framework adapters.
- Enum: Enumerations for well-defined constant sets.

## Design Guidelines

- Keep the Presentation layer thin; avoid direct persistence access.
- Encapsulate domain rules inside Entities and validate at boundaries.
- Services coordinate workflows; prefer small and cohesive methods.
- Repositories abstract persistence details; avoid leaking vendor types.
- DTOs should be simple, immutable where practical, and serialization-friendly.
- Utilities are stateless and focused; avoid embedding business logic.
- Exceptions carry actionable messages and preserve root causes.

## Generating Javadoc

You can generate HTML Javadoc using Maven:
