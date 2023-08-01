CREATE OR REPLACE FUNCTION fn_is_user_receiver(p_user_id uuid, p_exchange_id uuid = NULL)
    RETURNS BOOLEAN
AS
$$
DECLARE
    v_receiver_user_id    uuid;
    v_exchange_id         uuid;
    v_proposed_article_id uuid;
    v_article_id          uuid;
BEGIN

    IF p_exchange_id IS NOT NULL THEN
        SELECT a.user_id
        INTO v_receiver_user_id
        FROM articles a
                 JOIN proposed_articles pa ON a.id = pa.article_id
                 JOIN exchanges e ON pa.id = e.proposed_article_id
        WHERE e.id = p_exchange_id;

        RETURN v_receiver_user_id = p_user_id;
    END IF;


    SELECT pa.article_id, pa.proposed_article_id
    INTO v_article_id, v_proposed_article_id
    FROM articles a,
         proposed_articles pa
    WHERE a.id = p_user_id
      AND pa.article_id = a.id;

    IF v_proposed_article_id IS NOT NULL THEN
        SELECT e.id
        INTO v_exchange_id
        FROM exchanges e,
             proposed_articles pa
        WHERE pa.id = v_proposed_article_id
          AND pa.article_id = v_article_id;
    ELSE
        SELECT e.id
        INTO v_exchange_id
        FROM exchanges e,
             proposed_articles pa
        WHERE pa.id = v_proposed_article_id
          AND pa.article_id = v_article_id;
    END IF;


END;
$$ LANGUAGE plpgsql;