package com.example.hangman.exceptions;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp,
                                String message,
                                String details) {

}
