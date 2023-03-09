package com.example.hangman.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest
{

   @InjectMocks
   private GameService gameService;

   @BeforeEach
   void setup()
   {
      MockitoAnnotations.initMocks(this);
   }

   @Test
   void calculateWordRemaining()
   {
      Set<Character> guessedWords = new HashSet<>(Arrays.asList('e'));
      String result = gameService.calculateWordRemaining(guessedWords, "eggie".toCharArray());
      assertEquals(result, "e___e");
   }
}
