package com.example.hangman.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse
{
   private int id;
   private int guessesRemaining;
   private String word;
   private Set<Character> lettersRemaining;
   private Set<Character> lettersGuessed;
   private String message;

   private GameResponse(GameResponseBuilder builder) {
      this.id = builder.id;
      this.guessesRemaining = builder.guessesRemaining;
      this.word = builder.word;
      this.lettersRemaining = builder.lettersRemaining;
      this.lettersGuessed = builder.lettersGuessed;
      this.message = builder.message;
   }

   public static class GameResponseBuilder
   {
      private int id;
      private int guessesRemaining;
      private String word;
      private Set<Character> lettersRemaining;
      private Set<Character> lettersGuessed;
      private String message;

      public GameResponseBuilder id(int id) {
         this.id = id;
         return this;
      }
      public GameResponseBuilder guessesRemaining(int guessesRemaining) {
         this.guessesRemaining = guessesRemaining;
         return this;
      }
      public GameResponseBuilder word(String word) {
         this.word = word;
         return this;
      }
      public GameResponseBuilder lettersRemaining(Set<Character> lettersRemaining) {
         this.lettersRemaining = lettersRemaining;
         return this;
      }
      public GameResponseBuilder lettersGuessed(Set<Character> lettersGuessed) {
         this.lettersGuessed = lettersGuessed;
         return this;
      }
      public GameResponseBuilder message(String message) {
         this.message = message;
         return this;
      }

      public GameResponse build() {
         GameResponse user =  new GameResponse(this);
         return user;
      }
   }
}
