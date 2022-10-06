package com.vscoding.tutorial.control;

import com.vscoding.tutorial.exception.LogReadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for extracting logs based on a regex expression.
 */
@Service
public class LogReader {
  /**
   * Regex pattern for reading logs
   */
  private final Pattern pattern;
  /**
   * CSV headers
   */
  private final List<String> headers;
  /**
   * File to parse
   */
  private final File file;

  public LogReader(@Value("${app.logparser.regex}") String regex,
                   @Value("${app.logparser.inputPath}") String path,
                   @Value("${app.logparser.headers}") List<String> headers) {
    this.pattern = Pattern.compile(regex);
    this.headers = headers;
    this.file = new File(path);
  }

  /**
   * Read a file and extract all regex groups as header - value map.
   *
   * @return {@link List} list of log data
   */
  public List<Map<String, String>> read() {
    if (file == null) {
      throw new LogReadException("Provided file is null.");
    }

    var result = new ArrayList<Map<String, String>>();

    try (var reader = new BufferedReader(new FileReader(file))) {
      reader.lines()
              .map(pattern::matcher)
              .filter(Matcher::matches)
              .map(this::map)
              .forEach(result::add);
    } catch (FileNotFoundException e) {
      throw new LogReadException("File '" + file.getAbsolutePath() + "' could not be found.");
    } catch (Exception e) {
      throw new LogReadException("Exception occurred while reading '" + file.getAbsolutePath() + "'.", e);
    }

    return result;
  }

  /**
   * Extract log data and match regex groups to headers on index bases
   *
   * @param matcher {@link Matcher} applied to a log line
   * @return {@link Map} log data key = header, value = regex group
   */
  private Map<String, String> map(Matcher matcher) {
    var result = new HashMap<String, String>();

    // The first regex group is the full matched line
    for (int i = 1; i <= matcher.groupCount(); i++) {
      if (headers.size() >= i) {
        result.put(headers.get(i - 1), matcher.group(i));
      }
    }

    return result;
  }
}
