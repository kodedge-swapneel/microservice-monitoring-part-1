# Monitoring Microservice using Prometheus and Grafana

## [Click here to watch the video for demonstration.](https://youtu.be/6uc3zHywWIw)

In this project, I have demonstrated how to monitor microservice using Prometheus and created dashboard in Grafana to visualize the metrics.

### Prerequisites:
 - [Docker](https://docs.docker.com/engine/install/) or [Docker alternative - Colima](https://github.com/abiosoft/colima)
 - IDE and JDK


## Perform following steps to run the project:

## To run springboot microservice :
1. Pull this repository.
2. Open terminal and then cd into this project repository (i.e. microservice-monitoring-part-1)
3. Then `cd spring-monitoring-demo`
4. Build project using command : `./gradlew clean build`
5. Start docker in your machine
6. Build the docker image using command : `docker build -t spring-monitoring-demo:latest .`
7. Run microservice using command : `docker run -p 8080:8080 spring-monitoring-demo`
8. Verify by calling one of the api from the browser like `http://localhost:8080/visitApi` It should return response as `visitApi called : 1.0`
9. So here microservice is deployed successfully in the docker.

## To setup Promethues and Grafana:
1. Open new terminal.
2. cd into app-monitoring dir. e.g. `cd  microservice-monitoring-part-1/app-monitoring`
3. Run the command `docker-compose up`
4. Note that if you get permission issue with /etc dir. Then run command with sudo like this: `sudo docker-compose up`
5. Now open promethues web UI in browser using `localhost:9090`
6. Open Grafana using url: `localhost:3000`
7. Login to Grafana using `username:admin and password:admin`
8. And to import dashboard, search dashboard here : [grafana/dashboards](https://grafana.com/grafana/dashboards/). For example try with dashboard id: 11378
9. Also try to create dashboard for custom metrics. For more information, [watch the video](https://youtu.be/6uc3zHywWIw).


## Sample prometheus query for Histogram metrics:

```
histogram_quantile(0.5, sum by(le) (rate(http_request_histogram_example_bucket[$__rate_interval])))
```

### Explanation :

### 1. rate(http_request_histogram_example_bucket[$__rate_interval]):

 - `rate(...)`: This function calculates the per-second rate of change of the http_request_histogram_example_bucket histogram metric.
 - `$__rate_interval`: This is a variable that typically represents the time interval for the rate calculation, allowing dynamic adjustment based on the query range.
### 2.sum by(le) (...):

 - `sum by(le)`: This part of the query groups the results by the `le` label, which typically represents the upper bounds of histogram buckets.
 - The result is the sum of the rates for each le label.

### 3.histogram_quantile(0.5, ...):

 `histogram_quantile(0.5, ...)`: This function calculates the 50th percentile (median) of a histogram based on the provided quantile (0.5) and the grouped rates.

### Putting it all together:

- The query is designed to calculate the median response time or duration from a histogram of HTTP request durations represented by the `http_request_histogram_example_bucket` metric.

- The `rate(...)` part calculates the per-second rate of change of the histogram, providing an estimate of the rate at which HTTP requests are falling into different duration buckets.

- The `sum by(le) (...)` part groups the results by the upper bounds of histogram buckets (`le` labels) and calculates the sum of rates for each bucket.

- The `histogram_quantile(0.5, ...)` function then calculates the 50th percentile (median) of the histogram based on the grouped rates. This gives you the duration at which 50% of the observed HTTP requests have a duration less than or equal to this value.

- In summary, the query helps you understand the median response time distribution of HTTP requests over a specified time interval. 
- The result provides insight into the typical duration of requests in your system. 
- Adjustments can be made to the `$__rate_interval` variable based on the desired time range for analysis.
