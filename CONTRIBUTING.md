# Contributing

## Development Setup

```bash
# Prerequisites: Java 17+, Maven 3.8+, Docker (optional)

make dev    # Run with H2 in-memory database
make test   # Run unit + integration tests
```

## Code Style

- All code must compile with `mvn clean compile`
- All tests must pass with `mvn test`
- Use meaningful names — no abbreviations in public API
- Keep methods focused and small (prefer < 20 lines)
- Validate all inputs in DTOs and controllers

## Pull Request Checklist

- [ ] Code compiles
- [ ] Tests pass
- [ ] New endpoints have integration tests
- [ ] API changes documented
