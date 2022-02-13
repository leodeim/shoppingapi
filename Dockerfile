FROM gradle:7.3.3-jdk11
WORKDIR /api
COPY . .
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN dos2unix ./gradlew
RUN chmod +x ./gradlew
RUN ./gradlew clean
RUN ./gradlew build
CMD ./gradlew bootRun
