NAME := hangman

.DEFAULT_GOAL := help

.PHONY: help
help:  ## Display this help
	@awk 'BEGIN {FS = ":.*##"; printf "\n\033[1mUsage:\033[0m\n  make <target> \033[36m\033[0m\n\n\033[1mTargets:\033[0m\n"} /^[a-zA-Z0-9_-]+:.*?##/ { printf "  \033[36m%-25s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)


.PHONY: all
all: build start  ## Run build, start

.PHONY: build
build:  ## Build docker images
	@echo Building Docker image \for: $(NAME)
	mvn spring-boot:build-image -Dsonar.skip=True -Dmaven.test.skip=true
	@echo Finished building.

.PHONY: start
start:  ## Start service (run docker container)
	@echo Starting service: $(NAME)
	docker compose up -d ats
	@echo Service is up.

.PHONY: stop
stop:  ## Stop service (stop docker container)
	@echo Stopping service: $(NAME)
	docker compose down

.PHONY: clean
clean:  ## Remove docker images
	docker ps -qa --filter name=$(NAME) | xargs docker stop
	docker images --format '{{.Repository}}:{{.Tag}}' | grep $(NAME) | xargs docker rmi

