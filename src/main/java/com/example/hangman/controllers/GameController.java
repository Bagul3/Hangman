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

   @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<GameResponse> create(
      @RequestBody GameCreationRequest gameCreationRequest)
      throws Exception
   {
      return new ResponseEntity<>(gameService.createGame(gameCreationRequest), HttpStatus.CREATED);
   }

   @PutMapping(value = "/{id}/guess", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<GameResponse> guess(
      @PathVariable(value = "id")int id,
      @RequestBody GameGuessRequest guessedLetter)
   {
      return new ResponseEntity<>(gameService.guessMade(guessedLetter, id), HttpStatus.OK);
   }

   @DeleteMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<String> delete(@PathVariable int id)
   {
      gameService.deleteGame(id);
      return new ResponseEntity<>("Successfully deleted game", HttpStatus.OK);
   }
}
