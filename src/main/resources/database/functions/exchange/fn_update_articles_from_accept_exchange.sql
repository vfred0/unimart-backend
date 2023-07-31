CREATE OR REPLACE FUNCTION fn_update_articles_from_accept_exchange(
    p_exchange_id UUID,
    p_proposed_article_id UUID
) RETURNS VOID AS
$$
DECLARE
    v_proposed_article_id UUID;
BEGIN

    SELECT id
    INTO v_proposed_article_id
    FROM proposed_articles
    WHERE proposed_article_id = p_proposed_article_id;

    UPDATE exchanges
    SET proposed_article_id = v_proposed_article_id
    WHERE id = p_exchange_id;

    PERFORM fn_update_articles_from_deleted(p_proposed_article_id, TRUE);
END;
$$ LANGUAGE plpgsql;

SELECT fn_update_articles_from_accept_exchange
           ('d76fe44a-ebbb-4289-8eb7-c74617c2a09d',
            'dece83ed-baf1-434b-8a4e-730b9031a315',
            'c90772de-c54c-4283-9ed7-b07447fd9435');