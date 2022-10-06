package com.vscoding.tutorial.control;

import com.vscoding.tutorial.ApplicationProfiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile(ApplicationProfiles.NOT_TEST)
public class LogParserStarter {
  private final LogParser logParser;

  @PostConstruct
  public void execute(){
    log.info("Execute log parser.");
    logParser.parse();
  }
}
