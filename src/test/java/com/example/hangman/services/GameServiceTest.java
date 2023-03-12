package com.example.hangman.services;

import com.example.hangman.exceptions.GameNotFoundException;
import com.example.hangman.generated.jooq.tables.pojos.Game;
import com.example.hangman.models.requests.GameCreationRequest;
import com.example.hangman.models.requests.GameGuessRequest;
import com.example.hangman.models.responses.GameResponse;
import com.example.hangman.repositories.GameRepository;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameServiceTest
{

   @InjectMocks
   private GameService gameService;

   private Timestamp timestamp;

   @Mock
   private GameRepository gameRepository;

   @BeforeEach
   void setUp()
   {
      MockitoAnnotations.initMocks(this);
      timestamp = new Timestamp(System.currentTimeMillis());
   }

   @Test
   void testCreateGame()
   {
      int GAME_ID = 1;
      GameCreationRequest gameCreationRequest = buildGameCreationRequest();
      when(gameRepository.insertGame(gameCreationRequest.getWord(), gameCreationRequest.getNumberOfGuesses())).thenReturn(GAME_ID);
      GameResponse result = gameService.createGame(gameCreationRequest);
      assertEquals(result.getWord(), "_______");
      assertEquals(result.getId(), GAME_ID);
   }

   @Test
   void testGuessMade()
   {
      int GAME_ID = 1;
      Game game = buildGameObject();
      int GUESSES_REMAINING = 8;
      GameGuessRequest gameGuessRequest = new GameGuessRequest('u');
      when(gameRepository.getGameRecord(GAME_ID)).thenReturn(game);
      doNothing().when(gameRepository).updateGame(any(Character[].class), anyInt(), anyInt());
      GameResponse gameResponse = gameService.guessMade(gameGuessRequest, GAME_ID);

      verify(gameRepository, times(1)).updateGame(any(),anyInt(), anyInt());
      assertEquals(gameResponse.getWord(), "bubb___");
      assertEquals(gameResponse.getGuessesRemaining(), GUESSES_REMAINING);
      assertEquals(gameResponse.getLettersGuessed(), new HashSet<>(Arrays.asList('a', 'b', 'c', 'u')));
      assertEquals(gameResponse.getLettersRemaining(), new HashSet<>(Arrays.asList('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v','w', 'x', 'y', 'z')));
   }

   @Test
   void testGuessMadeFullWord()
   {
      int GAME_ID = 1;
      Game game = buildGameObjectAlmostFinished();
      int GUESSES_REMAINING = 7;
      GameGuessRequest gameGuessRequest = new GameGuessRequest('e');
      when(gameRepository.getGameRecord(GAME_ID)).thenReturn(game);
      doNothing().when(gameRepository).updateGame(any(Character[].class), anyInt(), anyInt());
      GameResponse gameResponse = gameService.guessMade(gameGuessRequest, GAME_ID);

      verify(gameRepository, times(1)).setEndGame(any(),anyInt(), anyInt());
      assertEquals(gameResponse.getWord(), "bubbles");
      assertEquals(gameResponse.getGuessesRemaining(), GUESSES_REMAINING);
      assertEquals(gameResponse.getMessage(), "Game over");
      assertEquals(gameResponse.getLettersGuessed(), new HashSet<>(Arrays.asList('u', 'b', 'l', 'e', 's')));
      assertEquals(gameResponse.getLettersRemaining(), new HashSet<>(Arrays.asList('a', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 't', 'v','w', 'x', 'y', 'z')));
   }

   @Test
   void testGuessMadeWhenGameHasEnded()
   {
      int GAME_ID = 1;
      Game game = buildGameObjectFinished();
      GameGuessRequest gameGuessRequest = new GameGuessRequest('e');
      when(gameRepository.getGameRecord(GAME_ID)).thenReturn(game);
      Throwable exception = assertThrows(IllegalArgumentException.class, () -> gameService.guessMade(gameGuessRequest, GAME_ID));
      assertEquals(exception.getMessage(), "Game has ended.");
   }

   @Test
   void testGuessMadeGameNotFound()
   {
      int GAME_ID = 1;
      GameGuessRequest gameGuessRequest = new GameGuessRequest('e');
      when(gameRepository.getGameRecord(GAME_ID)).thenReturn(null);
      Throwable exception = assertThrows(GameNotFoundException.class, () -> gameService.guessMade(gameGuessRequest, GAME_ID));
      assertEquals(exception.getMessage(), String.format("Could not find game with id: {%s}", GAME_ID));
   }

   @Test
   void testGuessMadeLetterAlreadyGuessed()
   {
      int GAME_ID = 1;
      GameGuessRequest gameGuessRequest = new GameGuessRequest('c');
      when(gameRepository.getGameRecord(GAME_ID)).thenReturn(buildGameObject());
      Throwable exception = assertThrows(IllegalArgumentException.class, () -> gameService.guessMade(gameGuessRequest, GAME_ID));
      assertEquals(exception.getMessage(), String.format("Letter {%s} has already been guessed.", gameGuessRequest.getLetter()));
   }

   @Test
   void deleteGame()
   {
      int GAME_ID = 1;
      doNothing().when(gameRepository).deleteGame(GAME_ID);
      gameService.deleteGame(GAME_ID);
      verify(gameRepository, times(GAME_ID)).deleteGame(GAME_ID);
   }

   private Game buildGameObject()
   {
      Game game = new Game();
      game.setId(1);
      game.setGuessedLetters("a","b", "c");
      game.setNumberOfGuesses(3);
      game.setNumberOfGuessesAllowed(12);
      game.setWord("bubbles");
      game.setStartTime(timestamp);
      game.setLastUpdated(timestamp);
      return game;
   }

   private Game buildGameObjectAlmostFinished()
   {
      Game game = new Game();
      game.setId(1);
      game.setGuessedLetters("b", "u", "l", "s");
      game.setNumberOfGuesses(4);
      game.setNumberOfGuessesAllowed(12);
      game.setWord("bubbles");
      game.setStartTime(timestamp);
      game.setLastUpdated(timestamp);
      return game;
   }

   private Game buildGameObjectFinished()
   {
      Game game = new Game();
      game.setId(1);
      game.setGuessedLetters("b", "u", "l", "s", "e");
      game.setNumberOfGuesses(4);
      game.setNumberOfGuessesAllowed(12);
      game.setWord("bubbles");
      game.setEndTime(timestamp);
      game.setStartTime(timestamp);
      game.setLastUpdated(timestamp);
      return game;
   }

   private GameCreationRequest buildGameCreationRequest()
   {
      return new GameCreationRequest("bubbles", 12);
   }
}
