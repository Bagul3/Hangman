package com.example.hangman.controllers;

import com.example.hangman.models.requests.GameCreationRequest;
import com.example.hangman.models.requests.GameGuessRequest;
import com.example.hangman.models.responses.GameResponse;
import com.example.hangman.services.GameService;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(GameController.class)
@ContextConfiguration(classes = {GameController.class})
@ActiveProfiles("test")
public class GameControllerTest
{
   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private GameService gameService;

   @Test
   public void testCreateGame() throws Exception {
      GameCreationRequest gameCreationRequest = new GameCreationRequest("apple", 12);
      GameResponse expectedResponse = new GameResponse.GameResponseBuilder()
         .id(1)
         .guessesRemaining(6)
         .word("_____")
         .lettersRemaining(Set.of('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'))
         .lettersGuessed(Set.of())
         .message("Game created successfully")
         .build();
      when(gameService.createGame(gameCreationRequest)).thenReturn(expectedResponse);

      mockMvc.perform(post("/game")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"word\": \"apple\",  \"numberOfGuesses\": 12 }"))
         .andExpect(status().isCreated())
         .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
         .andExpect(jsonPath("$.guessesRemaining").value(expectedResponse.getGuessesRemaining()))
         .andExpect(jsonPath("$.word").value(expectedResponse.getWord()));
   }

   @Test
   public void testMakeGuess() throws Exception
   {
      GameGuessRequest request = new GameGuessRequest();
      request.setLetter('a');

      GameResponse response = new GameResponse();
      response.setId(1);
      response.setWord("_a___a_");
      response.setGuessesRemaining(8);

      when(gameService.guessMade(request, 1)).thenReturn(response);

      mockMvc.perform(put("/game/1/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"letter\": \"a\" }"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.id").value(1))
         .andExpect(jsonPath("$.word").value("_a___a_"))
         .andExpect(jsonPath("$.guessesRemaining").value(8));
   }

   @Test
   public void testDeleteGame() throws Exception
   {
      doNothing().when(gameService).deleteGame(anyInt());

      mockMvc.perform(delete("/game/1/delete"))
         .andExpect(status().isOk());
   }
}
