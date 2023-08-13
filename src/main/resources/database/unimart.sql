CREATE TYPE categories AS ENUM (
    'TEXT_BOOKS_EDUCATIONAL_MATERIAL',
    'OFFICE_SUPPLIES',
    'ELECTRONICS',
    'FURNITURE_AND_DECORATION',
    'CLOTHING',
    'LABORATORY_MATERIAL',
    'MUSICAL_INSTRUMENTS',
    'SPORTING_GOODS',
    'ARTS_HANDICRAFTS',
    'TOYS_GAMES'
    );

CREATE TYPE genders AS ENUM (
    'MALE',
    'FEMALE',
    'UNISEX'
    );

CREATE TYPE states AS ENUM (
    'NEW',
    'PREOWNED',
    'USED',
    'LOW_QUALITY'
    );

CREATE TYPE type_articles AS ENUM (
    'PUBLISHED',
    'PROPOSED',
    'EXCHANGED'
    );

CREATE TABLE users
(
    id               UUID             NOT NULL DEFAULT gen_random_uuid(),
    photo            VARCHAR(60)      NOT NULL,
    username         VARCHAR(15)      NOT NULL,
    name             VARCHAR(60)      NOT NULL,
    about            VARCHAR(250)     NOT NULL,
    rating           DOUBLE PRECISION NOT NULL DEFAULT 0,
    number_exchanges SMALLINT         NOT NULL DEFAULT 0,
    number_whatsapp  VARCHAR(10)      NOT NULL,
    password         VARCHAR(32)      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT ck_users_rating CHECK (rating >= 0 AND rating <= 5),
    CONSTRAINT uq_users_number_whatsapp UNIQUE (number_whatsapp)
);

CREATE TABLE articles
(
    id                UUID          NOT NULL DEFAULT gen_random_uuid(),
    user_id           UUID          NOT NULL,
    title             VARCHAR(60)   NOT NULL,
    description       VARCHAR(250)  NOT NULL,
    category          categories    NOT NULL DEFAULT 'TEXT_BOOKS_EDUCATIONAL_MATERIAL',
    state             states        NOT NULL DEFAULT 'NEW',
    type_article      type_articles NOT NULL DEFAULT type_articles.PUBLISHED,
    gender            genders                DEFAULT NULL,
    numbers_proposals SMALLINT      NOT NULL DEFAULT 0,
    date              TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_articles PRIMARY KEY (id),
    CONSTRAINT fk_articles_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT ck_articles_numbers_proposals CHECK (numbers_proposals >= 0)
);

CREATE TABLE article_images
(
    article_id UUID        NOT NULL,
    image      VARCHAR(60) NOT NULL,
    CONSTRAINT pk_article_images PRIMARY KEY (article_id),
    CONSTRAINT fk_article_images_article_id FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE CASCADE
);

CREATE TABLE proposal
(
    id                  UUID NOT NULL DEFAULT gen_random_uuid(),
    receiver_article_id UUID NOT NULL,
    proposer_article_id UUID NOT NULL,
    CONSTRAINT pk_proposed_articles PRIMARY KEY (id),
    CONSTRAINT fk_proposed_articles_receiver_article_id FOREIGN KEY (receiver_article_id) REFERENCES articles (id) ON DELETE CASCADE,
    CONSTRAINT fk_proposed_articles_proposer_article_id FOREIGN KEY (proposer_article_id) REFERENCES articles (id) ON DELETE CASCADE,
    CONSTRAINT ck_proposed_articles_receiver_article_id_proposer_article_id CHECK (receiver_article_id <> proposer_article_id),
    CONSTRAINT uq_proposed_articles_proposer_article_id_receiver_article_id UNIQUE (proposer_article_id)
);

CREATE TABLE ratings
(
    id                    UUID         NOT NULL DEFAULT gen_random_uuid(),
    user_id_who_was_rated UUID,
    user_id_who_rated     UUID,
    score                 SMALLINT     NOT NULL DEFAULT 1,
    comment               VARCHAR(100) NOT NULL,
    date                  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_ratings PRIMARY KEY (id),
    CONSTRAINT fk_ratings_user_id_who_was_rated FOREIGN KEY (user_id_who_was_rated) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_ratings_user_id_who_rated FOREIGN KEY (user_id_who_rated) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT ck_ratings_user_id_who_was_rated_user_id_who_rated CHECK (user_id_who_was_rated <> user_id_who_rated),
    CONSTRAINT ck_ratings_score CHECK (score >= 1 AND score <= 5)
);

CREATE TABLE exchanges
(
    id                  UUID      NOT NULL DEFAULT gen_random_uuid(),
    proposed_article_id UUID      NOT NULL,
    receiver_rating_id  UUID,
    proposer_rating_id  UUID,
    date                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_made             BOOLEAN   NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_exchanges PRIMARY KEY (id),
    CONSTRAINT fk_exchanges_proposed_article_id FOREIGN KEY (proposed_article_id) REFERENCES proposal (id) ON DELETE CASCADE,
    CONSTRAINT fk_exchanges_receiver_rating_id FOREIGN KEY (receiver_rating_id) REFERENCES ratings (id) ON DELETE CASCADE,
    CONSTRAINT fk_exchanges_proposer_rating_id FOREIGN KEY (proposer_rating_id) REFERENCES ratings (id) ON DELETE CASCADE
);