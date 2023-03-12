package com.example.hangman.controllers;

import com.example.hangman.models.requests.GameCreationRequest;
import com.example.hangman.models.responses.GameResponse;
import com.example.hangman.models.requests.GameGuessRequest;
import com.example.hangman.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/game")
@CrossOrigin
@RestController
@Slf4j
public class GameController
{
   private final GameService gameService;

   public GameController(GameService gameService)
   {
      this.gameService = gameService;
   }

   /**
    * REST endpoint for creating a new game.
    *
    * @param gameCreationRequest the game creation request
    * @return the game response
    * @throws Exception if there was an error creating the game
    */
   @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<GameResponse> create(
      @RequestBody GameCreationRequest gameCreationRequest)
      throws Exception
   {
      return new ResponseEntity<>(gameService.createGame(gameCreationRequest), HttpStatus.CREATED);
   }

   /**
    * REST endpoint for making a guess on an existing game.
    *
    * @param id the game ID
    * @param guessedLetter the guessed letter
    * @return the game response
    */
   @PutMapping(value = "/{id}/guess", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<GameResponse> guess(
      @PathVariable(value = "id")int id,
      @RequestBody GameGuessRequest guessedLetter)
   {
      return new ResponseEntity<>(gameService.guessMade(guessedLetter, id), HttpStatus.OK);
   }

   /**
    * REST endpoint for deleting an existing game.
    *
    * @param id the game ID
    * @return a response entity with a success message
    */
   @DeleteMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<String> delete(@PathVariable int id)
   {
      gameService.deleteGame(id);
      return new ResponseEntity<>("Successfully deleted game", HttpStatus.OK);
   }
}
