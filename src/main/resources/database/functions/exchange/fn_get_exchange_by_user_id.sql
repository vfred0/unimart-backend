CREATE OR REPLACE FUNCTION fn_get_exchange_by_user_id(p_user_id uuid)
    RETURNS TABLE
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
            )
AS
$$
DECLARE
    v_exchange_id_to_exchange uuid;
    v_user_id                 uuid;
    v_user_name               VARCHAR(60);
    v_user_photo              VARCHAR(20);
    v_article_to_exchange     VARCHAR(60);
    v_article_to_receive      VARCHAR(60);
    v_date                    TIMESTAMP;
    v_article_id              uuid;
    v_has_been_rated          BOOLEAN;
    v_is_discarded            BOOLEAN;
    v_proposed_article_id     uuid;
BEGIN
    SELECT e.id,
           pa.article_id,
           pa.proposed_article_id,
           e.date
    INTO v_exchange_id_to_exchange, v_article_id, v_proposed_article_id, v_date
    FROM articles a,
         proposed_articles pa,
         exchanges e
    WHERE a.id = pa.article_id
      AND pa.id = e.proposed_article_id
      AND a.user_id = p_user_id;

    IF v_exchange_id_to_exchange IS NULL THEN
        SELECT e.id,
               pa.proposed_article_id,
               pa.article_id,
               e.date
        INTO v_exchange_id_to_exchange, v_article_id, v_proposed_article_id, v_date
        FROM articles a,
             proposed_articles pa,
             exchanges e
        WHERE a.id = pa.proposed_article_id
          AND pa.id = e.proposed_article_id
          AND a.user_id = p_user_id;
    END IF;

    v_article_to_exchange := (SELECT title FROM articles a WHERE a.id = v_article_id);
    v_article_to_receive := (SELECT title FROM articles a WHERE a.id = v_proposed_article_id);

    SELECT u.id, u.name, u.photo
    INTO v_user_id, v_user_name, v_user_photo
    FROM users u,
         articles a
    WHERE u.id = a.user_id
      AND a.id = v_proposed_article_id;


    IF fn_is_user_receiver(p_user_id, v_exchange_id_to_exchange) THEN
        RAISE NOTICE 'User is receiver';
        SELECT receiver_rating_id INTO v_has_been_rated FROM exchanges e WHERE e.id = v_exchange_id_to_exchange;
        IF v_has_been_rated IS NULL THEN
            v_has_been_rated := FALSE;
        ELSE
            v_has_been_rated := TRUE;
        END IF;
    ELSE
        RAISE NOTICE 'User is proposer';
        SELECT proposer_rating_id INTO v_has_been_rated FROM exchanges e WHERE e.id = v_exchange_id_to_exchange;
        IF v_has_been_rated IS NULL THEN
            v_has_been_rated := FALSE;
        ELSE
            v_has_been_rated := TRUE;
        END IF;
    END IF;

    SELECT NOT is_made INTO v_is_discarded FROM exchanges e WHERE e.id = v_exchange_id_to_exchange;

    RETURN QUERY SELECT v_exchange_id_to_exchange,
                        v_user_id,
                        v_user_name,
                        v_user_photo,
                        v_article_to_exchange,
                        v_article_to_receive,
                        v_has_been_rated,
                        v_is_discarded,
                        v_date;
END;
$$ LANGUAGE plpgsql;

SELECT *
FROM fn_get_exchange_by_user_id('1d8d1580-6a2f-489b-b533-32e4647aa255')
