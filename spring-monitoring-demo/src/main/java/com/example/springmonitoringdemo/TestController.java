package com.example.springmonitoringdemo;

import io.micrometer.core.instrument.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@RestController
public class TestController {

    private Counter visitCounter;
    private Timer timer;
    private DistributionSummary httpRequestsDurationHistogram;

    private List<Integer> queue = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    public TestController(MeterRegistry registry) {

        visitCounter = Counter.builder("visit_counter")
                .tags("counter-tag", "visitors")
                .description("Number of visits to the api")
                .register(registry);

        timer = Timer.builder("custom_time_recorder")
                .tags("timer-tag", "api-response")
                .description("Time required for the api")
                .register(registry);

        Gauge.builder("queue_size", queue, queue -> queue.size())
                .register(registry);

        httpRequestsDurationHistogram = DistributionSummary.builder("http_request_histogram_example")
                .description("Histogram example")
                .publishPercentileHistogram()
                .register(registry);

    }
    @GetMapping("/visitApi")
    public String visitCounter() {
        visitCounter.increment();
        return "visitApi called : "+visitCounter.count();
    }

    @GetMapping("/getResponseTime")
    public String timerExample() throws InterruptedException {
        Timer.Sample sample = Timer.start();

        System.out.println("Doing some work");
        Thread.sleep(getRandomNumber(500, 1000));
        if(queue.size() > 0) {
            queue.remove(0);
        }

        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000000);
        System.out.println("Total response time of API: " + responseTimeInMilliSeconds + " ms");

        return "ResponseTime example api called :"+ responseTimeInMilliSeconds;
    }

    @GetMapping("/getQueueSize")
    public String gaugeExample() {
        int number = getRandomNumber(500, 2000);
        queue.add(number);
        return "Gauge example api called: "+queue.size();
    }

    @GetMapping("/histogram")
    public String histogramExample() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        Thread.sleep(getRandomNumber(10, 1000));

        long duration = System.currentTimeMillis() - startTime;

        httpRequestsDurationHistogram.record(duration);
        return "histogram api called :"+duration;
    }


    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
