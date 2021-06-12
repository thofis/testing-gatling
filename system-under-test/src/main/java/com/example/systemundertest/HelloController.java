package com.example.systemundertest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class HelloController {

  @Value("${hello.delay.lower_limit}")
  int delayLowerLimit = 0;
  @Value("${hello.delay.upper_limit}")
  int delayUpperLimit = 0;

  private Random random = new Random();

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "N/A") final String name) throws InterruptedException {
    var message = String.format("Hello %s!", name);
    int delay = delayLowerLimit + random.nextInt(delayUpperLimit - delayLowerLimit);
    Thread.sleep(delay);
    log.info("message: {} with delay: {} ms", message, delay);
    return message;
  }
}
