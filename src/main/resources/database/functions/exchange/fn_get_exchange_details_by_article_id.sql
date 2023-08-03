CREATE OR REPLACE FUNCTION fn_exchange_details_by_article_id(p_article_id UUID)
    RETURNS TABLE
            (
                accept_proposals             BOOLEAN,
                receiver_user_id_for_article UUID,
                proposers_user_ids           UUID[]
            )
AS
$$
DECLARE
    v_accept_proposals             BOOLEAN;
    v_receiver_user_id_for_article UUID;
    v_proposer_user_id_for_article UUID;
    v_proposers_user_ids           UUID[];
    v_proposed_article_id          UUID;
    cur_proposed_articles CURSOR FOR
        SELECT proposer_article_id
        FROM proposed_articles
        WHERE receiver_article_id = p_article_id;
BEGIN
    v_accept_proposals := (SELECT COALESCE(BOOL_AND(NOT e.is_made), TRUE)
                           FROM proposed_articles pa,
                                exchanges e
                           WHERE (pa.receiver_article_id = p_article_id OR pa.proposer_article_id = p_article_id)
                             AND e.proposed_article_id = pa.id);
    OPEN cur_proposed_articles;
    LOOP
        FETCH cur_proposed_articles INTO v_proposed_article_id;
        EXIT WHEN NOT FOUND;
        SELECT user_id
        INTO v_proposer_user_id_for_article
        FROM articles
        WHERE id = v_proposed_article_id;
        v_proposers_user_ids := ARRAY_APPEND(v_proposers_user_ids, v_proposer_user_id_for_article);
    END LOOP;
    CLOSE cur_proposed_articles;

    SELECT user_id
    INTO v_receiver_user_id_for_article
    FROM articles
    WHERE id = (SELECT receiver_article_id FROM proposed_articles WHERE proposer_article_id = p_article_id);

    RETURN QUERY
        SELECT v_accept_proposals,
               v_receiver_user_id_for_article,
               v_proposers_user_ids;
END;
$$ LANGUAGE plpgsql;


SELECT * FROM fn_exchange_details_by_article_id('ba1409c7-38a6-4de3-89e4-9dd3e09babd5');

DROP FUNCTION fn_exchange_details_by_article_id(UUID);
