package com.vscoding.tutorial.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogParser {
  private final LogWriter writer;
  private final LogReader reader;

  @PostConstruct
  public void parse() {
    try {
      var logData = reader.read();
      writer.write(logData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
