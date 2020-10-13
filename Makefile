DOCKER_TAG=swe-project

build: Dockerfile setup.sh
	docker build -t ${DOCKER_TAG} .

shell:
	docker run -it ${DOCKER_TAG}
