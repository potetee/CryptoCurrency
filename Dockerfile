FROM openjdk
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/CryptoCurrency-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]