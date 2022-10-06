package com.vscoding.tutorial.control;

import com.vscoding.tutorial.exception.LogReaderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class LogReaderTest {
  private static final String PATH = LogReaderTest.class.getResource("application.log").getPath();
  private static final String REGEX = ".*'(.*)' created='(\\d+)', deleted='(\\d+)'.*";
  private static final List<String> HEADERS = Arrays.asList("name", "created", "deleted");

  @Test
  @DisplayName("Check read, when file could not be found.")
  void read_fileMissing() {
    // Given
    var sut = new LogReader("C:/asdas/dsa", REGEX, HEADERS);

    // When
    assertThrows(LogReaderException.class, sut::read);
  }

  @Test
  void read_regexBroken() {
    // When
    assertThrows(PatternSyntaxException.class, () -> new LogReader(PATH, "*", HEADERS));
  }

  @Test
  void read_headerEmpty() {
    // Given
    var sut = new LogReader(PATH, REGEX, Collections.emptyList());

    // When
    var logData = sut.read();

    // Then
    assertEquals(2, logData.size());
    assertTrue(logData.get(0).isEmpty());
    assertTrue(logData.get(1).isEmpty());
  }

  @Test
  void read_headerSmaller() {
    // Given
    var sut = new LogReader(PATH, REGEX, Arrays.asList("name", "created"));

    // When
    var logData = sut.read();

    // Then
    assertEquals(2, logData.size());
    assertEquals(2, logData.get(1).size());

    var firstEntry = logData.get(0);
    assertEquals(2, firstEntry.size());
    assertEquals("Jobs", firstEntry.get("name"));
    assertEquals("2", firstEntry.get("created"));
  }

  @Test
  void read_headerBigger() {
    // Given
    var sut = new LogReader(PATH, REGEX, Arrays.asList("name", "created","deleted","time"));

    // When
    var logData = sut.read();

    // Then
    assertEquals(2, logData.size());

    var firstEntry = logData.get(0);
    assertEquals(3, firstEntry.size());
    assertEquals("Jobs", firstEntry.get("name"));
    assertEquals("2", firstEntry.get("created"));
    assertEquals("15", firstEntry.get("deleted"));
  }

  @Test
  void read_successfully() {
    // Given
    var sut = new LogReader(PATH, REGEX, HEADERS);

    // When
    var logData = sut.read();

    // Then
    assertEquals(2, logData.size());

    var firstEntry = logData.get(0);
    assertEquals(3, firstEntry.size());
    assertEquals("Jobs", firstEntry.get("name"));
    assertEquals("2", firstEntry.get("created"));
    assertEquals("15", firstEntry.get("deleted"));
  }
}
