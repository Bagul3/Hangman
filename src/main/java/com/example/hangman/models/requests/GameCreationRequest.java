package com.example.hangman.models.requests;

import com.example.hangman.helpers.StringLowerCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameCreationRequest
{
   @StringLowerCase
   private String word;
   private int numberOfGuesses;
}
