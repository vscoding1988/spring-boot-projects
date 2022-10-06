package com.vscoding.tutorial.exception;

/**
 * Bundles all exceptions thrown while creating csv.
 */
public class LogWriterException extends RuntimeException{
  public LogWriterException(String message) {
    super(message);
  }

  public LogWriterException(String message, Throwable cause) {
    super(message, cause);
  }
}
