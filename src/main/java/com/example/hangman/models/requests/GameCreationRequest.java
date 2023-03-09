package com.example.hangman.models.requests;

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
   private String word;
   private int numberOfGuesses;
}
