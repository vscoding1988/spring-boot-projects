package com.vscoding.tutorial;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(ApplicationProfiles.TEST)
class ApplicationTest {

  @Test
  void contextLoad(){
    assertTrue(true);
  }
}
