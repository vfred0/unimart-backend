CREATE OR REPLACE PROCEDURE proc_delete_exchange_by_id(p_exchange_id UUID)
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_proposed_article_id uuid;
BEGIN
    SELECT proposed_article_id
    INTO v_proposed_article_id
    FROM exchanges
    WHERE id = p_exchange_id;

    DELETE FROM exchanges WHERE id = p_exchange_id;

    DELETE
    FROM proposed_articles
    WHERE id = v_proposed_article_id;
END;
$$;
