package com.vscoding.tutorial.exception;

/**
 * Bundles all exceptions thrown while reading log.
 */
public class LogReaderException extends RuntimeException{
  public LogReaderException(String message) {
    super(message);
  }

  public LogReaderException(String message, Throwable cause) {
    super(message, cause);
  }
}
