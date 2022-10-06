package com.vscoding.tutorial.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LogWriter {
  public void write(List<Map<String,String>> logData) {
    log.info("Execute csv creation.");
  }
}
