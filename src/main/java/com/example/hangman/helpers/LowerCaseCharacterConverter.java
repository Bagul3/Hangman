package com.example.hangman.helpers;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LowerCaseCharacterConverter extends StdConverter<Character, Character>
{
   @Override
   public Character convert(Character value) {
      if (value == null){
         return null;
      }
      return Character.toLowerCase(value);
   }
}
