package com.example.hangman.repositories;

import com.example.hangman.generated.jooq.tables.pojos.Game;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class GameRepositoryTest {

   @Autowired
   private GameRepository gameRepository;

   @Test
   public void testInsertGame() {
      String word = "example";
      int numberOfGuesses = 10;
      Integer gameId = gameRepository.insertGame(word, numberOfGuesses);
      assertNotNull(gameId);
      Game game = gameRepository.getGameRecord(gameId);
      assertEquals(word, game.getWord());
      assertEquals(numberOfGuesses, game.getNumberOfGuessesAllowed());
      assertEquals(0, game.getNumberOfGuesses());
   }

   @Test
   public void testGetGameRecord() {
      Game game = new Game();
      game.setWord("example");
      game.setNumberOfGuessesAllowed(10);
      game.setNumberOfGuesses(0);

      String word = "example";
      int numberOfGuesses = 10;
      Integer gameId = gameRepository.insertGame(word, numberOfGuesses);

      Game gameRecord = gameRepository.getGameRecord(gameId);
      assertEquals(game.getWord(), gameRecord.getWord());
      assertEquals(game.getNumberOfGuessesAllowed(), gameRecord.getNumberOfGuessesAllowed());
      assertEquals(game.getNumberOfGuesses(), gameRecord.getNumberOfGuesses());
   }

   @Test
   public void testUpdateGame() {
      String word = "example";
      int numberOfGuesses = 3;
      Integer gameId = gameRepository.insertGame(word, numberOfGuesses);
      Character[] guessedLetters = {'e', 'x', 'a'};

      gameRepository.updateGame(guessedLetters, numberOfGuesses, gameId);
      Game gameRecord = gameRepository.getGameRecord(gameId);
      assertEquals(word, gameRecord.getWord());
   }

   @Test
   public void testSetEndGame() {
      String word = "example";
      int numberOfGuesses = 6;
      Integer gameId = gameRepository.insertGame(word, numberOfGuesses);
      Character[] guessedLetters = {'e', 'x', 'a', 'm', 'p', 'l'};

      gameRepository.setEndGame(guessedLetters, numberOfGuesses, gameId);
      Game gameRecord = gameRepository.getGameRecord(gameId);
      assertEquals(word, gameRecord.getWord());
      assertNotNull(gameRecord.getEndTime());
   }

   @Test
   public void testDeleteGame() {
      String word = "example";
      int numberOfGuesses = 10;
      Integer gameId = gameRepository.insertGame(word, numberOfGuesses);
      Game gameRecord = gameRepository.getGameRecord(gameId);
      assertNotNull(gameRecord);
      gameRepository.deleteGame(gameId);
      gameRecord = gameRepository.getGameRecord(gameId);
      assertNull(gameRecord);
   }
}
