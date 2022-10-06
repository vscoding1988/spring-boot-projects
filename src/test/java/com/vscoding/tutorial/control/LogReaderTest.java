package com.vscoding.tutorial.control;

import com.vscoding.tutorial.exception.LogReadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogReaderTest {
  private static final String REGEX = ".* '(.*)' created='(\\d+)', deleted='(\\d+)'.*";
  private static final List<String> HEADERS = Arrays.asList("name", "created", "deleted");
  private static final String PATH = LogReaderTest.class.getResource("success.log").getPath();

  @Test
  @DisplayName("Check parse, when file path is null.")
  void parse_fileNull() {
    // When
    assertThrows(NullPointerException.class, () -> new LogReader(REGEX, null, HEADERS));
  }

  @Test
  @DisplayName("Check parse, when file could not be found.")
  void parse_notFound() {
    // Given
    var sut = new LogReader(REGEX, "/weirdPath", HEADERS);

    // When
    assertThrows(LogReadException.class, sut::read);
  }

  @Test
  @DisplayName("Check parse, when headers are null.")
  void parse_headerNull() {
    // Given
    var sut = new LogReader(REGEX, PATH, null);

    // When
    assertThrows(LogReadException.class, sut::read);
  }

  @Test
  @DisplayName("Check parse, when regex is null.")
  void parse_regexNull() {
    assertThrows(NullPointerException.class, () -> new LogReader(null, PATH, HEADERS));
  }

  @Test
  @DisplayName("Check parse, when file could be parsed.")
  void parse_success() {
    // Given
    var sut = new LogReader(REGEX, PATH, HEADERS);

    // When
    var result = sut.read();

    // Then
    assertEquals(2, result.size());

    var firstResult = result.get(0);
    assertEquals("Jobs", firstResult.get("name"));
    assertEquals("2", firstResult.get("created"));
    assertEquals("15", firstResult.get("deleted"));
  }

  @Test
  @DisplayName("Check parse, when configuration has more headers, then regex groups.")
  void parse_moreHeaders() {
    // Given
    var sut = new LogReader(REGEX, PATH, Arrays.asList("name", "created", "deleted", "updated"));

    // When
    var result = sut.read();

    // Then
    assertEquals(2, result.size());

    var firstResult = result.get(0);
    assertEquals(3, firstResult.size());
    assertEquals("Jobs", firstResult.get("name"));
    assertEquals("2", firstResult.get("created"));
    assertEquals("15", firstResult.get("deleted"));
  }

  @Test
  @DisplayName("Check parse, when configuration has more regex groups, then headers.")
  void parse_moreGroups() {
    // Given
    var sut = new LogReader(REGEX, PATH, Arrays.asList("name", "created"));

    // When
    var result = sut.read();

    // Then
    assertEquals(2, result.size());

    var firstResult = result.get(0);
    assertEquals(2, firstResult.size());
    assertEquals("Jobs", firstResult.get("name"));
    assertEquals("2", firstResult.get("created"));
  }
}
