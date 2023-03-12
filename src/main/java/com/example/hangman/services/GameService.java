package com.example.hangman.services;

import com.example.hangman.exceptions.GameNotFoundException;
import com.example.hangman.generated.jooq.tables.pojos.Game;
import com.example.hangman.models.requests.GameCreationRequest;
import com.example.hangman.models.requests.GameGuessRequest;
import com.example.hangman.models.responses.GameResponse;
import com.example.hangman.repositories.GameRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameService
{
   private final GameRepository gameRepository;

   public GameService(GameRepository gameRepository)
   {
      this.gameRepository = gameRepository;
   }

   public GameResponse createGame(GameCreationRequest gameCreationRequest)
   {
      int id = gameRepository.insertGame(gameCreationRequest.getWord(), gameCreationRequest.getNumberOfGuesses());
      String remainingWord = calculateWordRemaining(new HashSet<>(), gameCreationRequest.getWord().toCharArray());
      Set<Character> lettersRemaining = lettersRemaining(new HashSet<>());
      return new GameResponse.GameResponseBuilder()
         .id(id)
         .word(remainingWord)
         .guessesRemaining(gameCreationRequest.getNumberOfGuesses())
         .lettersRemaining(lettersRemaining)
         .build();
   }

   public GameResponse guessMade(GameGuessRequest gameGuessRequest, int id)
   {
      Game game = gameRepository.getGameRecord(id);
      if (game == null)
         throw new GameNotFoundException(String.format("Could not find game with id: {%s}", id));

      if (isGameFinished(game.getEndTime()))
         throw new IllegalArgumentException("Game has ended.");

      Set<Character> charactersGuessed = updateCharactersGuessed(gameGuessRequest, game);
      String remainingWord = calculateWordRemaining(charactersGuessed, game.getWord().toCharArray());
      Set<Character> lettersRemaining = lettersRemaining(charactersGuessed);
      int guessesRemaining = calculateNumberOfGuessesRemaining(game.getNumberOfGuesses(), game.getNumberOfGuessesAllowed());

      if (hasGameEnded(id, game, charactersGuessed, remainingWord))
         return new GameResponse.GameResponseBuilder()
            .id(id).word(remainingWord)
            .guessesRemaining(guessesRemaining)
            .lettersRemaining(lettersRemaining)
            .lettersGuessed(charactersGuessed)
            .message("Game over")
            .build();

      updateGame(id, game.getNumberOfGuesses()+1, charactersGuessed);
      return new GameResponse.GameResponseBuilder()
         .id(id)
         .word(remainingWord)
         .guessesRemaining(guessesRemaining)
         .lettersRemaining(lettersRemaining)
         .lettersGuessed(charactersGuessed)
         .build();
   }

   public void deleteGame(int id)
   {
      gameRepository.deleteGame(id);
   }

   private int calculateNumberOfGuessesRemaining(int guessesMade, int guessesAllowed)
   {
      int incrementGuess = 1;
      return guessesAllowed - (guessesMade + incrementGuess);
   }

   private Set<Character> updateCharactersGuessed(GameGuessRequest gameGuessRequest, Game game)
   {
      Set<Character> charactersGuessed = convertToCharSet(game.getGuessedLetters());
      if (hasLetterAlreadyBeenGuessed(gameGuessRequest.getLetter(), charactersGuessed))
         throw new IllegalArgumentException(String.format("Letter {%s} has already been guessed.", gameGuessRequest.getLetter()));
      charactersGuessed.add(gameGuessRequest.getLetter());
      return charactersGuessed;
   }

   private boolean hasGameEnded(int id, Game game, Set<Character> charactersGuessed, String remainingWord)
   {
      if (isMatch(remainingWord, game.getWord()) || (game.getNumberOfGuesses()+1 > game.getNumberOfGuessesAllowed()))
      {
         gameRepository.setEndGame(charactersGuessed.toArray(Character[]::new), game.getNumberOfGuesses()+1, id);
         return true;
      }
      return false;
   }

   private boolean isGameFinished(Timestamp endTime)
   {
      return endTime != null;
   }

   private boolean hasLetterAlreadyBeenGuessed(Character guessedLetter, Set<Character> guessedLetters)
   {
      return guessedLetters.contains(guessedLetter);
   }

   private void updateGame(int id, int numberOfGuesses, Set<Character> charactersGuessed)
   {
      gameRepository.updateGame(charactersGuessed.toArray(Character[]::new), numberOfGuesses+1, id);
   }

   private boolean isMatch(String word, String guessedWord)
   {
      return word.equals(guessedWord);
   }

   private String calculateWordRemaining(Set<Character> charactersGuessed, char[] word)
   {
      char[] output = new char[word.length];
      Arrays.fill(output, '_');

      for(char character: charactersGuessed)
      {
         for(int j =0; j < word.length; j++)
         {
            if (character == word[j])
            {
               output[j] = character;
            }
         }
      }

      return String.copyValueOf(output);
   }

   private Set<Character> lettersRemaining(Set<Character> lettersGuessed)
   {
      Set<Character>
         alphabet = new HashSet<>(
         Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v','w', 'x', 'y', 'z'));
      alphabet.removeAll(lettersGuessed);
      return alphabet;
   }

   private Set<Character> convertToCharSet(String[] words) {
      List<Character> array = new ArrayList<>();
      char[] charArray = String.join("", words).toCharArray();
      for (char character: charArray)
      {
         array.add(character);
      }
      return new HashSet<>(array);
   }
}
