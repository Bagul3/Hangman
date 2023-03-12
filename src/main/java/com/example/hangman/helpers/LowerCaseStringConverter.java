package com.example.hangman.helpers;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LowerCaseStringConverter extends StdConverter<String, String>
{
   @Override
   public String convert(String value) {
      if (value == null){
         return null;
      }
      return value.toLowerCase();
   }
}
