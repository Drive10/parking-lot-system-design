.PHONY: build test clean run dev docker-up docker-down lint

build:
	mvn clean package -DskipTests

test:
	mvn test

clean:
	mvn clean
	rm -rf target/

run:
	mvn spring-boot:run

dev:
	mvn spring-boot:run -Dspring-boot.run.profiles=dev

docker-up:
	docker compose up --build -d

docker-down:
	docker compose down

lint:
	mvn checkstyle:check

coverage:
	mvn verify
	open target/site/jacoco/index.html 2>/dev/null || true

all: clean build test
