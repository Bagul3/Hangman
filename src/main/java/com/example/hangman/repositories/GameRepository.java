package com.example.hangman.repositories;

import com.example.hangman.generated.jooq.tables.daos.GameDao;
import com.example.hangman.generated.jooq.tables.pojos.Game;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.example.hangman.generated.jooq.tables.Game.GAME;

@Component
@Slf4j
public class GameRepository extends GameDao
{
   @Autowired
   private DSLContext context;

   public Integer insertGame(String word, int numberOfGuesses)
   {
      Map<Object, Object> colValueMap = new HashMap<>();
      colValueMap.put(GAME.WORD, word);
      colValueMap.put(GAME.NUMBER_OF_GUESSES_ALLOWED, numberOfGuesses);
      colValueMap.put(GAME.NUMBER_OF_GUESSES, 0);
      colValueMap.put(GAME.GUESSED_LETTERS, new String[]{});
      org.jooq.Record record = context
         .insertInto(GAME)
         .columns((Collection)colValueMap.keySet())
         .values(colValueMap.values())
         .returning(GAME.ID)
         .fetchOne();

      if (record != null)
         return record.get(GAME.ID);
      return null;
   }

   public Game getGameRecord(int id)
   {
      return context
         .select(GAME.asterisk())
         .from(GAME)
         .where(GAME.ID.eq(id))
         .fetchOneInto(Game.class);
   }

   public void updateGame(Character[] guessedLetters, int numberOfGuesses, int id)
   {
      Timestamp time = new Timestamp(System.currentTimeMillis());
      Map<Object, Object> colValueMap = new HashMap<>();
      colValueMap.put(GAME.GUESSED_LETTERS, guessedLetters);
      colValueMap.put(GAME.LAST_UPDATED, time);
      colValueMap.put(GAME.NUMBER_OF_GUESSES, numberOfGuesses);
      context
         .update(GAME)
         .set(colValueMap)
         .where(GAME.ID.eq(id))
         .execute();
   }

   public void setEndGame(Character[] guessedLetters, int numberOfGuesses, int id)
   {
      Timestamp time = new Timestamp(System.currentTimeMillis());
      Map<Object, Object> colValueMap = new HashMap<>();
      colValueMap.put(GAME.LAST_UPDATED, time);
      colValueMap.put(GAME.END_TIME, time);
      colValueMap.put(GAME.GUESSED_LETTERS, guessedLetters);
      colValueMap.put(GAME.LAST_UPDATED, time);
      colValueMap.put(GAME.NUMBER_OF_GUESSES, numberOfGuesses);
      context
         .update(GAME)
         .set(colValueMap)
         .where(GAME.ID.eq(id))
         .execute();
   }

   public void deleteGame(int id)
   {
      context
         .delete(GAME)
         .where(GAME.ID.eq(id))
         .execute();
   }
}
