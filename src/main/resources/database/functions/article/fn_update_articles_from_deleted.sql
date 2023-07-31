CREATE OR REPLACE FUNCTION fn_update_articles_from_deleted(p_article_id UUID, p_only_update_number_proposals BOOLEAN) RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_article_id_to_update_numbers_proposals UUID;
    v_numbers_proposals                      SMALLINT;
    v_article_id_to_update                   UUID;
    v_proposed_article_id                    UUID;
    cur_articles CURSOR FOR
        SELECT proposed_article_id
        FROM proposed_articles
        WHERE article_id = p_article_id;
BEGIN
    IF NOT p_only_update_number_proposals THEN
        OPEN cur_articles;
        LOOP
            FETCH cur_articles INTO v_proposed_article_id;
            EXIT WHEN v_proposed_article_id IS NULL;
            UPDATE articles
            SET type_article = 'PUBLISHED'
            WHERE id = v_proposed_article_id;
        END LOOP;
        CLOSE cur_articles;

        UPDATE articles
        SET numbers_proposals = 0,
            type_article      = 'PUBLISHED'
        WHERE id = p_article_id;

    ELSE
        UPDATE articles
        SET type_article = 'PUBLISHED'
        WHERE id = p_article_id;
    END IF;

    v_article_id_to_update_numbers_proposals :=
            (SELECT article_id FROM proposed_articles WHERE proposed_article_id = p_article_id);

    IF v_article_id_to_update_numbers_proposals IS NOT NULL THEN
        v_numbers_proposals :=
                (SELECT numbers_proposals FROM articles WHERE id = v_article_id_to_update_numbers_proposals);

        IF v_numbers_proposals > 0 THEN
            v_numbers_proposals := v_numbers_proposals - 1;
        END IF;

        v_article_id_to_update := v_article_id_to_update_numbers_proposals;
        UPDATE articles
        SET numbers_proposals = v_numbers_proposals
        WHERE id = v_article_id_to_update;
    END IF;
END;
$$;

SELECT fn_update_articles_from_deleted('0efe4b9e-e301-40a9-bb3c-66877fc0af3a', TRUE);

