FROM ubuntu
WORKDIR /setup

COPY setup.sh .
RUN bash setup.sh

CMD service mysql start && bash
