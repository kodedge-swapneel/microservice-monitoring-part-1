# Monitoring Microservice using Prometheus and Grafana

## [Click here to watch the video for demonstration.]()

In this project, I have demonstrated how to monitor microservice using Prometheus and create dashboard in Grafana to visualize the metrics.

### Prerequisites:
 - [Docker](https://docs.docker.com/engine/install/) or [Docker alternative - Colima](https://github.com/abiosoft/colima)
 - IDE and JDK


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
