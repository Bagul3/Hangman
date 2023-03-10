/*
 * This file is generated by jOOQ.
 */
package com.example.hangman.generated.jooq.tables.daos;


import com.example.hangman.generated.jooq.tables.Game;
import com.example.hangman.generated.jooq.tables.records.GameRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GameDao extends DAOImpl<GameRecord, com.example.hangman.generated.jooq.tables.pojos.Game, Integer> {

    /**
     * Create a new GameDao without any configuration
     */
    public GameDao() {
        super(Game.GAME, com.example.hangman.generated.jooq.tables.pojos.Game.class);
    }

    /**
     * Create a new GameDao with an attached configuration
     */
    public GameDao(Configuration configuration) {
        super(Game.GAME, com.example.hangman.generated.jooq.tables.pojos.Game.class, configuration);
    }

    @Override
    public Integer getId(com.example.hangman.generated.jooq.tables.pojos.Game object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Game.GAME.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchById(Integer... values) {
        return fetch(Game.GAME.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.example.hangman.generated.jooq.tables.pojos.Game fetchOneById(Integer value) {
        return fetchOne(Game.GAME.ID, value);
    }

    /**
     * Fetch records that have <code>guessed_letters BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfGuessedLetters(String[] lowerInclusive, String[] upperInclusive) {
        return fetchRange(Game.GAME.GUESSED_LETTERS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>guessed_letters IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByGuessedLetters(String[]... values) {
        return fetch(Game.GAME.GUESSED_LETTERS, values);
    }

    /**
     * Fetch records that have <code>number_of_guesses_allowed BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfNumberOfGuessesAllowed(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Game.GAME.NUMBER_OF_GUESSES_ALLOWED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>number_of_guesses_allowed IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByNumberOfGuessesAllowed(Integer... values) {
        return fetch(Game.GAME.NUMBER_OF_GUESSES_ALLOWED, values);
    }

    /**
     * Fetch records that have <code>number_of_guesses BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfNumberOfGuesses(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Game.GAME.NUMBER_OF_GUESSES, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>number_of_guesses IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByNumberOfGuesses(Integer... values) {
        return fetch(Game.GAME.NUMBER_OF_GUESSES, values);
    }

    /**
     * Fetch records that have <code>word BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfWord(String lowerInclusive, String upperInclusive) {
        return fetchRange(Game.GAME.WORD, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>word IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByWord(String... values) {
        return fetch(Game.GAME.WORD, values);
    }

    /**
     * Fetch records that have <code>start_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfStartTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(Game.GAME.START_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>start_time IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByStartTime(Timestamp... values) {
        return fetch(Game.GAME.START_TIME, values);
    }

    /**
     * Fetch records that have <code>end_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfEndTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(Game.GAME.END_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>end_time IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByEndTime(Timestamp... values) {
        return fetch(Game.GAME.END_TIME, values);
    }

    /**
     * Fetch records that have <code>last_updated BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchRangeOfLastUpdated(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(Game.GAME.LAST_UPDATED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_updated IN (values)</code>
     */
    public List<com.example.hangman.generated.jooq.tables.pojos.Game> fetchByLastUpdated(Timestamp... values) {
        return fetch(Game.GAME.LAST_UPDATED, values);
    }
}
