package com.vscoding.tutorial.exception;

/**
 * Exception bundling all errors thrown while writing a log.
 */
public class LogWriteException extends RuntimeException{
  public LogWriteException(String message, Throwable cause) {
    super(message, cause);
  }
}
