package com.example.hangman.models.requests;

import com.example.hangman.helpers.CharacterLowerCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameGuessRequest
{
   @CharacterLowerCase
   private Character letter;
}
