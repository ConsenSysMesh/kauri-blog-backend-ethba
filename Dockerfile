FROM openjdk:8-jre

RUN curl -sL https://deb.nodesource.com/setup_9.x | bash -
RUN apt-get update
RUN apt-get -y install nodejs
RUN apt-get -y install make
RUN apt-get -y install g++
RUN apt-get install -y wget git-core
RUN npm install -g truffle@4.1.5
CMD sh run-dbt.sh
ADD ./target/dbt.jar .
ADD docker-scripts/run-dbt.sh .

EXPOSE 8080