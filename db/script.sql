CREATE TABLE comment (
    id     BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    pet_id         BIGINT UNSIGNED NOT NULL,
    post_id        BIGINT UNSIGNED NOT NULL,
    content        VARCHAR(140) NOT NULL,
    date           DATETIME NOT NULL,
    active         BOOL NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE follower (
    id    BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    pet_follower   BIGINT UNSIGNED NOT NULL,
    pet_followed   BIGINT UNSIGNED NOT NULL,
    active         BOOL NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE enjoy (
    id  BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    pet_id    BIGINT UNSIGNED NOT NULL,
    post_id   BIGINT UNSIGNED NOT NULL,
    active    BOOL NOT NULL,
    PRIMARY KEY ( id )
); 

CREATE TABLE pet (
    id        BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    user_id       BIGINT UNSIGNED NOT NULL,
    name          VARCHAR(50) NOT NULL,
    species       VARCHAR(30) NOT NULL,
    description   VARCHAR(280),
    genre         VARCHAR(10) NOT NULL,
    photo         VARCHAR(1000) NOT NULL,
    active        BOOL NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE post (
    id       BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    pet_id        BIGINT UNSIGNED NOT NULL,
    photos        VARCHAR(1000),
    description   VARCHAR(280) NOT NULL,
    DATE          DATETIME NOT NULL,
    active        BOOL NOT NULL,
	PRIMARY KEY ( id )
);

CREATE TABLE user (
    id    BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    name       VARCHAR(100) NOT NULL,
    nickname   VARCHAR(50) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(80) NOT NULL,
    photo      VARCHAR(1000),
    active     BOOL NOT NULL,
    PRIMARY KEY ( id )
);


ALTER TABLE comment
    ADD CONSTRAINT comment_pet_fk FOREIGN KEY ( pet_id )
        REFERENCES pet ( id ) ON DELETE CASCADE;

ALTER TABLE comment
    ADD CONSTRAINT comment_post_fk FOREIGN KEY ( post_id )
        REFERENCES post ( id ) ON DELETE CASCADE;

ALTER TABLE follower
    ADD CONSTRAINT followed_pet_fk FOREIGN KEY ( pet_followed )
        REFERENCES pet ( id ) ON DELETE CASCADE;

ALTER TABLE follower
    ADD CONSTRAINT follower_pet_fk FOREIGN KEY ( pet_follower )
        REFERENCES pet ( id ) ON DELETE CASCADE;

ALTER TABLE enjoy
    ADD CONSTRAINT like_pet_fk FOREIGN KEY ( pet_id )
        REFERENCES pet ( id ) ON DELETE CASCADE;

ALTER TABLE enjoy
    ADD CONSTRAINT like_post_fk FOREIGN KEY ( post_id )
        REFERENCES post ( id ) ON DELETE CASCADE;

ALTER TABLE pet
    ADD CONSTRAINT pet_user_fk FOREIGN KEY ( user_id )
        REFERENCES user ( id ) ON DELETE CASCADE;

ALTER TABLE post
    ADD CONSTRAINT post_pet_fk FOREIGN KEY ( pet_id )
        REFERENCES pet ( id ) ON DELETE CASCADE;
