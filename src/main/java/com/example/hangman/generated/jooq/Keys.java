/*
 * This file is generated by jOOQ.
 */
package com.example.hangman.generated.jooq;


import com.example.hangman.generated.jooq.tables.FlywaySchemaHistory;
import com.example.hangman.generated.jooq.tables.Game;
import com.example.hangman.generated.jooq.tables.records.FlywaySchemaHistoryRecord;
import com.example.hangman.generated.jooq.tables.records.GameRecord;

import javax.annotation.processing.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<GameRecord, Integer> IDENTITY_GAME = Identities0.IDENTITY_GAME;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = UniqueKeys0.FLYWAY_SCHEMA_HISTORY_PK;
    public static final UniqueKey<GameRecord> PK_GAME_ID = UniqueKeys0.PK_GAME_ID;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<GameRecord, Integer> IDENTITY_GAME = Internal.createIdentity(Game.GAME, Game.GAME.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, "flyway_schema_history_pk", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK);
        public static final UniqueKey<GameRecord> PK_GAME_ID = Internal.createUniqueKey(Game.GAME, "pk_game_id", Game.GAME.ID);
    }
}