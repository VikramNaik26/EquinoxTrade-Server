package com.vikram.EquinoxTrade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthExcetion.class)
  public ResponseEntity<String> handleEmailAlreadyExistException(AuthExcetion ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }
}
