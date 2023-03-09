package com.example.hangman.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class EntityExceptionHandler
{
   @ExceptionHandler(Exception.class)
   public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception,
      WebRequest request) {
      ExceptionResponse exceptionResponse = new ExceptionResponse(
         LocalDateTime.now(), exception.getMessage(),
         request.getDescription(false));

      return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({GameNotFoundException.class})
   public final ResponseEntity<ExceptionResponse> handleGameNotFoundException(GameNotFoundException studentNotFoundException,
      WebRequest request) {
      ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
         studentNotFoundException.getMessage(),
         request.getDescription(false));

      return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
   }
}
