package com.vscoding.tutorial.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogParser {
  private final LogReader reader;
  private final LogWriter writer;

  public void parse(){
    try{
      var logData = reader.read();
      writer.write(logData);
    }catch (Exception e){
      log.error("Log parsing failed",e);
    }
  }
}
