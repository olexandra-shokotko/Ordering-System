package com.example.orderingsystem.exception;

import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = NonExistingElementAtSpecifiedIdException.class)
  public ResponseEntity<Object> NonExistingElementAtSpecifiedIdException(
      NonExistingElementAtSpecifiedIdException exception) {
    return new ResponseEntity<>("There is no element with this id", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = NotEnoughAvailableItemsException.class)
  public ResponseEntity<Object> NotEnoughAvailableItemsException(
      InvalidQuantityInputException exception) {
    return new ResponseEntity<>("Not enough available items", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ProductAlreadyExistsException.class)
  public ResponseEntity<Object> ProductAlreadyExistsException(
      ProductAlreadyExistsException exception) {
    return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  ResponseEntity<Object> InvalidQuantityInputException(ValidationException e) {
    return new ResponseEntity<>("Invalid quantity input: should be min 0", HttpStatus.BAD_REQUEST);
  }
}
