CREATE OR REPLACE FUNCTION fn_update_articles_from_made_exchange(
    p_exchange_id UUID,
    p_rating_id UUID,
    p_user_id UUID
) RETURNS VOID AS
$$
DECLARE
    v_article_id                  UUID;
    DECLARE v_proposed_article_id UUID;
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
      AND id NOT IN (SELECT id FROM exchanges WHERE id = p_exchange_id);

    UPDATE articles
    SET type_article = 'EXCHANGED'
    WHERE id = v_article_id
       OR id = v_proposed_article_id;

    IF fn_is_user_receiver(p_user_id, p_exchange_id) THEN
        UPDATE exchanges SET receiver_rating_id = p_rating_id WHERE id = p_exchange_id;
    ELSE
        UPDATE exchanges SET proposer_rating_id = p_rating_id WHERE id = p_exchange_id;
    END IF;

    UPDATE exchanges SET is_made = TRUE WHERE id = p_exchange_id;
END;
$$ LANGUAGE plpgsql;
