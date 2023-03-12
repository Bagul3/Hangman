package com.example.hangman.repositories;

import com.example.hangman.generated.jooq.tables.daos.GameDao;
import com.example.hangman.generated.jooq.tables.pojos.Game;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.example.hangman.generated.jooq.tables.Game.GAME;

@Component
public class GameRepository extends GameDao
{
   @Autowired
   private DSLContext context;

   public Integer insertGame(String word, int numberOfGuesses)
   {
      Map<Object, Object> columnValueMap = new HashMap<>();
      columnValueMap.put(GAME.WORD, word);
      columnValueMap.put(GAME.NUMBER_OF_GUESSES_ALLOWED, numberOfGuesses);
      columnValueMap.put(GAME.NUMBER_OF_GUESSES, 0);
      columnValueMap.put(GAME.GUESSED_LETTERS, new String[]{});
      org.jooq.Record record = context
         .insertInto(GAME)
         .columns((Collection)columnValueMap.keySet())
         .values(columnValueMap.values())
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
      Timestamp time = new Timestamp(Instant.now().getEpochSecond());
      Map<Object, Object> columnValueMap = new HashMap<>();
      columnValueMap.put(GAME.GUESSED_LETTERS, guessedLetters);
      columnValueMap.put(GAME.LAST_UPDATED, time);
      columnValueMap.put(GAME.NUMBER_OF_GUESSES, numberOfGuesses);
      context
         .update(GAME)
         .set(columnValueMap)
         .where(GAME.ID.eq(id))
         .execute();
   }

   public void setEndGame(Character[] guessedLetters, int numberOfGuesses, int id)
   {
      Timestamp time = new Timestamp(Instant.now().getEpochSecond());
      Map<Object, Object> columnValueMap = new HashMap<>();
      columnValueMap.put(GAME.LAST_UPDATED, time);
      columnValueMap.put(GAME.END_TIME, time);
      columnValueMap.put(GAME.GUESSED_LETTERS, guessedLetters);
      columnValueMap.put(GAME.NUMBER_OF_GUESSES, numberOfGuesses);
      context
         .update(GAME)
         .set(columnValueMap)
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
