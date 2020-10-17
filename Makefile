DOCKER_TAG=swe-project

build: Dockerfile hotel.war
	docker build -t ${DOCKER_TAG} .

compile: pom.xml
	mvn compile

war: pom.xml
	mvn package; mv target/*.war hotel.war

shell:
	docker run -p 8080:8080 -it ${DOCKER_TAG}
