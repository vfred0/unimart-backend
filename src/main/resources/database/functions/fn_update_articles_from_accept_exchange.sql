CREATE OR REPLACE FUNCTION fn_update_articles_from_accept_exchange(
    p_exchange_id UUID,
    p_article_id UUID,
    p_proposed_article_id UUID
) RETURNS VOID AS
$$
DECLARE
    v_exchange_id UUID;
BEGIN

    RAISE NOTICE 'p_article_id: %', p_article_id;
    RAISE NOTICE 'p_proposed_article_id: %', p_proposed_article_id;
    RAISE NOTICE 'p_exchange_id: %', p_exchange_id;

    UPDATE proposed_articles
    SET exchange_id = p_exchange_id
    WHERE proposed_article_id = p_proposed_article_id;

    SELECT exchange_id INTO v_exchange_id FROM proposed_articles WHERE proposed_article_id = p_article_id;

    PERFORM fn_update_articles_from_deleted(p_article_id, FALSE);
    PERFORM fn_update_articles_from_deleted(p_proposed_article_id, FALSE);

    DELETE
    FROM proposed_articles
    WHERE (article_id IN (p_article_id, p_proposed_article_id)
        OR proposed_article_id = p_article_id)
      AND exchange_id IS NULL;

END;
$$ LANGUAGE plpgsql;

SELECT fn_update_articles_from_accept_exchange
           ('d76fe44a-ebbb-4289-8eb7-c74617c2a09d',
            'dece83ed-baf1-434b-8a4e-730b9031a315',
            'c90772de-c54c-4283-9ed7-b07447fd9435');

SELECT *
FROM proposed_articles
WHERE exchange_id IS NULL;
WHERE proposed_article_id = '23b70fb2-08db-40cc-90e0-a34665ad8eba'