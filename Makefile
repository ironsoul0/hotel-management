DOCKER_TAG=swe-project

build: docker-compose.yml Dockerfile hotel.war
	docker-compose up
	#docker build -t ${DOCKER_TAG} .

hotel.war: src/ pom.xml
	mvn compile
	mvn package
	mv target/*.war hotel.war

compile: pom.xml
	mvn compile

war: pom.xml
	mvn package; mv target/*.war hotel.war

shell:
	docker run -p 8080:8080 -it ${DOCKER_TAG}
