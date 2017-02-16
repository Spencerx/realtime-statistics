#Real Time Statistics Rest API

## What does this project do?
This Rest API allows transactions (with a value and a timestamp) to be saved and to get statistics about the transactions that happened in the last 60 seconds.
To add a transaction, simple POST a json object with the data: "amount" and "timestamp" to the url: "/transactions".
To get statistics about the transactions in the last 60 seconds, just get the url: "/statistics", and it will return a json with the following statistics:
sum, avg, max, min, count



## How to run in
First you should compile it using: `mvn clean install`.
The project uses Spring Boot in order to easly run the web application.
You can run the web application by either running directly the jar in the "target/" folder: `java -jar realtime_statistics-0.0.1-SNAPSHOT.jar` 
Or just by running the spring-boot maven plugin: `mvn spring-boot:run`.

By default Spring boot starts on localhost on the port 8080, making the endpoints full URL be:
`http://localhost:8080/statistics` and `http://localhost:8080/transactions`