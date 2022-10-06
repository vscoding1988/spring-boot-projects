package com.vscoding.tutorial.control;

import ch.qos.logback.core.util.FileUtil;
import com.vscoding.tutorial.exception.LogWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LogWriter {
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private final String outputPath;
  private final String delimiter;
  private final List<String> headers;

  public LogWriter(@Value("${app.logparser.outputPath}") String outputPath,
                   @Value("${app.logparser.delimiter}") String delimiter,
                   @Value("${app.logparser.headers}") List<String> headers) {
    this.outputPath = outputPath;
    this.headers = headers;
    this.delimiter = delimiter;
  }

  public void write(List<Map<String, String>> logData) {
    try {
      var builder = new StringBuilder(String.join(delimiter, headers)).append(LINE_SEPARATOR);

      logData.stream()
              .map(this::toRow)
              .forEach(builder::append);

      Files.writeString(Path.of(outputPath), builder.toString());
    } catch (Exception e) {
      throw new LogWriteException("Error writing '" + outputPath + "'.", e);
    }
  }

  /**
   * Creates CSV row for log data, the column order is based on the header order
   *
   * @param data {@link Map} log data {header2:value2,header1:value1}
   * @return csv row "value1";"value2"\n
   */
  private String toRow(Map<String, String> data) {
    var values = new ArrayList<String>();

    for (var header : headers) {
      values.add(data.getOrDefault(header, ""));
    }

    return "\"" + String.join("\"" + delimiter + "\"", values) + "\"" + LINE_SEPARATOR;
  }
}
