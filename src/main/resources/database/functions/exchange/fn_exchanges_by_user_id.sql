CREATE OR REPLACE FUNCTION fn_exchanges_by_user_id(p_user_id uuid)
    RETURNS SETOF v_exchange
AS
$$
DECLARE
    v_exchange_id         uuid;
    v_user_id             uuid;
    v_user_name           VARCHAR(60);
    v_user_photo          VARCHAR(20);
    v_article_to_exchange VARCHAR(60);
    v_article_to_receive  VARCHAR(60);
    v_date                TIMESTAMP;
    v_article_id          uuid;
    v_has_been_rated      BOOLEAN;
    v_is_discarded        BOOLEAN;
    v_proposed_article_id uuid;
    cursor_exchanges CURSOR FOR
        SELECT e.id,
               pa.receiver_article_id,
               pa.proposer_article_id,
               e.date
        FROM exchanges e,
             proposed_articles pa,
             articles a
        WHERE a.user_id = p_user_id
          AND (a.id = pa.receiver_article_id OR a.id = pa.proposer_article_id)
          AND e.proposed_article_id = pa.id;
BEGIN


    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'v_exchange') THEN
        CREATE TEMP TABLE v_exchange
        (
            id                  uuid,
            user_id             uuid,
            user_name           VARCHAR(60),
            user_photo          VARCHAR(20),
            article_to_exchange VARCHAR(60),
            article_to_receive  VARCHAR(60),
            has_been_rated      BOOLEAN,
            is_discarded        BOOLEAN,
            date                TIMESTAMP
        );
    ELSE
        TRUNCATE TABLE v_exchange;
    END IF;

    OPEN cursor_exchanges;
    LOOP
        FETCH cursor_exchanges INTO v_exchange_id, v_article_id, v_proposed_article_id, v_date;
        EXIT WHEN v_exchange_id IS NULL;
        IF fn_is_user_receiver(p_user_id, v_exchange_id) THEN
            v_has_been_rated :=
                    (SELECT e.receiver_rating_id IS NOT NULL FROM exchanges e WHERE e.id = v_exchange_id);

            v_article_id := (SELECT pa.proposer_article_id
                             FROM proposed_articles pa,
                                  exchanges e
                             WHERE pa.receiver_article_id = v_article_id
                               AND e.id = v_exchange_id
                               AND pa.id = e.proposed_article_id);
            RAISE NOTICE 'receiver%', v_article_id;
        ELSE
            v_has_been_rated :=
                    (SELECT e.proposer_rating_id IS NOT NULL FROM exchanges e WHERE e.id = v_exchange_id);
            v_proposed_article_id :=
                    (SELECT pa.receiver_article_id
                     FROM proposed_articles pa,
                          exchanges e
                     WHERE e.id = v_exchange_id
                       AND pa.proposer_article_id = v_proposed_article_id);
        END IF;

        v_article_to_exchange := (SELECT title FROM articles a WHERE a.id = v_article_id);
        v_article_to_receive := (SELECT title FROM articles a WHERE a.id = v_proposed_article_id);

        SELECT u.id, u.name, u.photo
        INTO v_user_id, v_user_name, v_user_photo
        FROM users u,
             articles a
        WHERE u.id = a.user_id
          AND a.id = v_proposed_article_id;

        SELECT NOT is_made INTO v_is_discarded FROM exchanges e WHERE e.id = v_exchange_id;

        INSERT INTO v_exchange
        VALUES (v_exchange_id, v_user_id, v_user_name, v_user_photo, v_article_to_exchange,
                v_article_to_receive,
                v_has_been_rated, v_is_discarded, v_date);

    END LOOP;
    CLOSE cursor_exchanges;

    RETURN QUERY SELECT * FROM v_exchange;
END;
$$ LANGUAGE plpgsql;
