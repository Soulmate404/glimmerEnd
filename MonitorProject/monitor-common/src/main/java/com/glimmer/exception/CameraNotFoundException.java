package com.glimmer.exception;

public class CameraNotFoundException extends RuntimeException {
  public CameraNotFoundException(String message) {
    super(message);
  }
}
