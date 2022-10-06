package com.vscoding.tutorial.exception;

/**
 * Exception bundling all errors thrown while reading a log.
 */
public class LogReadException extends RuntimeException{
  public LogReadException(String message) {
    super(message);
  }

  public LogReadException(String message, Throwable cause) {
    super(message, cause);
  }
}
