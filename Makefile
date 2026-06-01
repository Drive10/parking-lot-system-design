.PHONY: build test clean run dev docker-up docker-down frontend-install frontend-dev frontend-build frontend-test

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

frontend-install:
	cd frontend && npm install

frontend-dev:
	cd frontend && npm run dev

frontend-build:
	cd frontend && npm run build

frontend-test:
	cd frontend && npm test

docker-up:
	docker compose up --build -d

docker-down:
	docker compose down

all: build test
