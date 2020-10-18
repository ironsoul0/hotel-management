FROM tomcat

RUN apt-get update && \
      apt-get install -y net-tools vim

ADD hotel.war /usr/local/tomcat/webapps
EXPOSE 8080

CMD ["catalina.sh", "run"]
