CREATE OR REPLACE FUNCTION fn_get_exchange_details_by_article_id(p_article_id UUID)
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
    v_exchange_id                  UUID;
    cur_proposed_articles CURSOR FOR
        SELECT proposed_article_id
        FROM proposed_articles
        WHERE article_id = p_article_id;
BEGIN

    SELECT e.id
    INTO v_exchange_id
    FROM exchanges e,
         proposed_articles pa
    WHERE pa.id = e.proposed_article_id;


    v_accept_proposals := NOT EXISTS(SELECT v_exchange_id
                                     FROM proposed_articles
                                     WHERE (article_id = p_article_id OR proposed_article_id = p_article_id)
                                       AND v_exchange_id IS NOT NULL);

    IF NOT v_accept_proposals THEN
        v_accept_proposals := (SELECT NOT is_made FROM exchanges WHERE id = '8858fe50-3c1a-4f02-869b-a569b4e72a0b');
    END IF;

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
FROM fn_get_exchange_details_by_article_id('d6c4b69f-07ff-41e2-a92e-ba20ea5e857a');
SELECT *
FROM fn_get_exchange_details_by_article_id('43a44270-e751-4bcc-bce8-53476ed024ae');
-- SELECT *
-- FROM fn_get_exchange_details_by_article_id('81f9a71c-ed30-4aa0-ac37-e675fa6248f1');
-- SELECT *
-- FROM fn_get_exchange_details_by_article_id('31aa3ae4-b661-4603-ab23-df4c12922002');
-- SELECT *
-- FROM fn_get_exchange_details_by_article_id('67ea7a47-72ce-48a6-bc40-3f70363d5a36');
-- DROP FUNCTION fn_get_exchange_details_by_article_id(UUID);
