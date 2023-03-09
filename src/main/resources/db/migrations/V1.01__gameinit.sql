CREATE TABLE game
(
    id       serial NOT NULL,
    guessed_letters  varchar[],
    number_of_guesses_allowed int,
    number_of_guesses int NOT NULL,
    word varchar NOT NULL,
    start_time timestamp DEFAULT now(),
    end_time timestamp,
    last_updated timestamp DEFAULT now(),
    constraint pk_game_id PRIMARY KEY (id)
);
