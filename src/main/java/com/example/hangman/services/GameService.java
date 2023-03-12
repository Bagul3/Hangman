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

   /**
    * Creates a new game based on the given game creation request.
    * @param gameCreationRequest The request object containing word and number of guesses.
    * @return A response object containing the game's ID, word, number of guesses remaining, and letters remaining.
    */
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

   /**
    * Makes a guess for the game with the given ID, based on the given guess request.
    * @param gameGuessRequest The request object containing the guessed letter.
    * @param id The ID of the game being guessed.
    * @return A response object containing the updated game state after the guess.
    * @throws GameNotFoundException If the game with the given ID cannot be found.
    * @throws IllegalArgumentException If the game has already ended or if the guessed letter has already been guessed.
    */
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

   /**
    * Deletes the game with the given ID.
    * @param id The ID of the game to delete.
    */
   public void deleteGame(int id)
   {
      gameRepository.deleteGame(id);
   }

   /**
    * Calculates the number of guesses remaining, based on the number of guesses made and the number of guesses allowed.
    * @param guessesMade The number of guesses that have been made.
    * @param guessesAllowed The maximum number of guesses allowed.
    * @return The number of guesses remaining.
    */
   private int calculateNumberOfGuessesRemaining(int guessesMade, int guessesAllowed)
   {
      int incrementGuess = 1;
      return guessesAllowed - (guessesMade + incrementGuess);
   }

   /**
    * Updates the set of characters that have been guessed, based on the given guess request and the current game state.
    * @param gameGuessRequest The guess request object containing the guessed letter.
    * @param game The current game state.
    * @return The updated set of guessed characters.
    * @throws IllegalArgumentException If the guessed letter has already been guessed.
    */
   private Set<Character> updateCharactersGuessed(GameGuessRequest gameGuessRequest, Game game)
   {
      Set<Character> charactersGuessed = convertToCharSet(game.getGuessedLetters());
      if (hasLetterAlreadyBeenGuessed(gameGuessRequest.getLetter(), charactersGuessed))
         throw new IllegalArgumentException(String.format("Letter {%s} has already been guessed.", gameGuessRequest.getLetter()));
      charactersGuessed.add(gameGuessRequest.getLetter());
      return charactersGuessed;
   }

   /**
    * Checks if the game has ended, and if so, updates the game state in the database and returns true.
    * Otherwise, returns false.
    * @param id The ID of the game being checked.
    * @param game The current game state.
    * @param charactersGuessed The set of characters that have been guessed so far.
    * @param remainingWord The current remaining word (with blanks for unguessed letters).
    * @return true if the game has ended, false otherwise.
    */
   private boolean hasGameEnded(int id, Game game, Set<Character> charactersGuessed, String remainingWord)
   {
      if (isMatch(remainingWord, game.getWord()) || (game.getNumberOfGuesses()+1 > game.getNumberOfGuessesAllowed()))
      {
         gameRepository.setEndGame(charactersGuessed.toArray(Character[]::new), game.getNumberOfGuesses()+1, id);
         return true;
      }
      return false;
   }

   /**
    * Checks if the game has ended based on the game end time.
    * @param endTime The end time of the game.
    * @return true if the game has ended, false otherwise.
    */
   private boolean isGameFinished(Timestamp endTime)
   {
      return endTime != null;
   }

   /**
    * Checks if the guessed letter has already been guessed.
    * @param guessedLetter The guessed letter.
    * @param guessedLetters The set of guessed letters.
    * @return true if the letter has already been guessed, false otherwise.
    */
   private boolean hasLetterAlreadyBeenGuessed(Character guessedLetter, Set<Character> guessedLetters)
   {
      return guessedLetters.contains(guessedLetter);
   }

   /**
    * Updates the game state in the database with the new number of guesses and the set of guessed letters.
    * @param id The ID of the game being updated.
    * @param numberOfGuesses The new number of guesses.
    * @param charactersGuessed The set of guessed letters.
    */
   private void updateGame(int id, int numberOfGuesses, Set<Character> charactersGuessed)
   {
      gameRepository.updateGame(charactersGuessed.toArray(Character[]::new), numberOfGuesses+1, id);
   }

   /**
    * Checks if the given word matches the guessed word.
    * @param word The actual word.
    * @param guessedWord The word with blanks for unguessed letters.
    * @return true if the given word matches the guessed word, false otherwise.
    */
   private boolean isMatch(String word, String guessedWord)
   {
      return word.equals(guessedWord);
   }

   /**
    * Calculates the remaining word from letters guessed
    * @param lettersGuessed Characters guessed.
    * @param word The word.
    * @return The remaining word
    */
   private String calculateWordRemaining(Set<Character> lettersGuessed, char[] word)
   {
      char[] output = new char[word.length];
      Arrays.fill(output, '_');

      for(char letter: lettersGuessed)
      {
         for(int j =0; j < word.length; j++)
         {
            if (letter == word[j])
            {
               output[j] = letter;
            }
         }
      }

      return String.copyValueOf(output);
   }

   /**
    * Finds the letters which have not been guessed
    * @param lettersGuessed Characters guessed.
    * @return Letters not yet guessed.
    */
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
