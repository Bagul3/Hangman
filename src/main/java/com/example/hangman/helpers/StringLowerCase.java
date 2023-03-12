package com.example.hangman.helpers;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(converter = LowerCaseStringConverter.class)
@JsonDeserialize(converter = LowerCaseStringConverter.class)
public @interface StringLowerCase {
}
