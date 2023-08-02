CREATE OR REPLACE FUNCTION fn_update_articles_from_made_exchange(
    p_exchange_id UUID,
    p_rating_id UUID,
    p_user_id_who_rated UUID,
    p_user_id_who_was_rated UUID
) RETURNS VOID AS
$$
DECLARE
    v_article_id          UUID;
    v_proposed_article_id UUID;
    v_user_id_who_rated   UUID;
    v_average_rating      NUMERIC;
BEGIN
    SELECT pa.article_id, pa.proposed_article_id
    INTO v_article_id, v_proposed_article_id
    FROM exchanges e,
         proposed_articles pa
    WHERE e.id = p_exchange_id
      AND pa.id = e.proposed_article_id;

    PERFORM fn_update_articles_from_deleted(v_article_id, FALSE);
    PERFORM fn_update_articles_from_deleted(v_proposed_article_id, FALSE);

    DELETE
    FROM proposed_articles
    WHERE (article_id IN (v_article_id, v_proposed_article_id)
        OR proposed_article_id = v_article_id)
      AND id NOT IN (SELECT e.proposed_article_id FROM exchanges e WHERE e.id = p_exchange_id);

    UPDATE articles
    SET type_article = 'EXCHANGED'
    WHERE id = v_article_id
       OR id = v_proposed_article_id;


    IF fn_is_user_receiver(p_user_id_who_rated, p_exchange_id) = TRUE THEN
        UPDATE exchanges SET receiver_rating_id = p_rating_id WHERE id = p_exchange_id;
    ELSE
        UPDATE exchanges SET proposer_rating_id = p_rating_id WHERE id = p_exchange_id;
    END IF;

    UPDATE exchanges SET is_made = TRUE WHERE id = p_exchange_id;

    UPDATE ratings
    SET user_id_who_rated     = p_user_id_who_rated,
        user_id_who_was_rated = p_user_id_who_was_rated
    WHERE id = p_rating_id;


    SELECT AVG(r.score)
    INTO v_average_rating
    FROM ratings r
    WHERE r.user_id_who_was_rated = p_user_id_who_was_rated;

    UPDATE users
    SET number_of_exchanges = number_of_exchanges + 1,
        rating              = v_average_rating
    WHERE id = p_user_id_who_was_rated;
END;
$$ LANGUAGE plpgsql;

-- Roxana
SELECT *
FROM fn_update_articles_from_made_exchange('7123d149-a96d-43f6-89df-03246ac467ab',
                                           '0b2d80df-cbe8-4db8-b19f-32ae37d14414',
                                           '56921875-e3e6-45b9-985c-114fccc1cae7');

SELECT *
FROM fn_update_articles_from_made_exchange('7123d149-a96d-43f6-89df-03246ac467ab',
                                           '0b2d80df-cbe8-4db8-b19f-32ae37d14414',
                                           '201cdc90-dad8-40aa-b923-e5d6d5ef2c9a');


SELECT fn_is_user_receiver('56921875-e3e6-45b9-985c-114fccc1cae7', '7123d149-a96d-43f6-89df-03246ac467ab')
SELECT fn_is_user_receiver('201cdc90-dad8-40aa-b923-e5d6d5ef2c9a', '7123d149-a96d-43f6-89df-03246ac467ab')

