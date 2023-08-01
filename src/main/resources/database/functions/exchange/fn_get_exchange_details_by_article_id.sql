CREATE OR REPLACE FUNCTION fn_exchange_details_by_article_id(p_article_id UUID)
    RETURNS TABLE
            (
                accept_proposals             BOOLEAN,
                receiver_user_id_for_article UUID,
                is_in_exchange               BOOLEAN,
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
    v_exchange_id                  UUID;
    cur_proposed_articles CURSOR FOR
        SELECT proposed_article_id
        FROM proposed_articles
        WHERE article_id = p_article_id;
BEGIN
    v_accept_proposals := (SELECT COALESCE(BOOL_AND(NOT e.is_made), TRUE)
                           FROM proposed_articles pa,
                                exchanges e
                           WHERE (pa.article_id = p_article_id OR pa.proposed_article_id = p_article_id)
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
    WHERE id = (SELECT article_id FROM proposed_articles WHERE proposed_article_id = p_article_id);

    RETURN QUERY
        SELECT v_accept_proposals,
               v_receiver_user_id_for_article,
               v_proposers_user_ids;
END;
$$ LANGUAGE plpgsql;

SELECT *
FROM fn_get_exchange_details_by_article_id('efe4b7b3-2dcf-4466-ac71-23055a3e8e10');


CREATE OR REPLACE FUNCTION fn_is_article_user_receiver(p_article_id UUID)
    RETURNS BOOLEAN
AS
$$
DECLARE
    v_article_id UUID;
BEGIN
    SELECT pa.article_id
    INTO v_article_id
    FROM proposed_articles pa
    WHERE pa.article_id = p_article_id;
    RETURN v_article_id IS NOT NULL;
END;
$$ LANGUAGE plpgsql;