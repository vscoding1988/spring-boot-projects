package com.vscoding.tutorial.control;

import com.vscoding.tutorial.exception.LogReaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LogReader {
  private final String path;
  private final Pattern pattern;
  private final List<String> headers;

  public LogReader(@Value("${app.logparser.inputPath}") String path,
                   @Value("${app.logparser.regex}") String regex,
                   @Value("${app.logparser.headers}") List<String> headers) {
    this.path = path;
    this.pattern = Pattern.compile(regex);
    this.headers = headers;
  }

  public List<Map<String, String>> read() {
    log.info("Execute log read for '{}'.", path);

    var file = new File(path);

    try (var reader = new BufferedReader(new FileReader(file))) {

      return reader.lines()
              .map(pattern::matcher)
              .filter(Matcher::matches)
              .map(this::parseRow)
              .collect(Collectors.toList());

    } catch (FileNotFoundException e) {
      throw new LogReaderException("File could not be found below '" + path + "'");
    } catch (Exception e) {
      throw new LogReaderException("File could not be processed below '" + path + "'", e);
    }
  }

  /**
   * Parse a log line, extract regex groups and create a map with headers based on the index.
   *
   * @param matcher {@link Matcher} for a log line
   * @return {@link Map} {"header1":"value1","header2":"value2"}
   */
  private Map<String, String> parseRow(Matcher matcher) {
    var result = new HashMap<String, String>();

    // The group with number 0 contains the full matched line, so we start with 1.
    // When matcher.groupCount == 3 this means there are 3 groups + the whole line, so you can access 4 groups
    for (int i = 1; i <= matcher.groupCount(); i++) {
      if (headers.size() >= i) {
        result.put(headers.get(i - 1), matcher.group(i));
      }
    }

    return result;
  }
}
