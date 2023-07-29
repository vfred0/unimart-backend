CREATE OR REPLACE FUNCTION fn_update_articles_from_deleted(p_article_id UUID, p_only_update_number_proposals BOOLEAN) RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    _article_id_to_update_numbers_proposals UUID;
    _numbers_proposals                      SMALLINT;
    _article_id_to_update                   UUID;
    _proposed_article_id                    UUID;
    cur_articles CURSOR FOR
        SELECT proposed_article_id
        FROM proposed_articles
        WHERE article_id = p_article_id;
BEGIN

    IF NOT p_only_update_number_proposals THEN
        OPEN cur_articles;
        LOOP
            FETCH cur_articles INTO _proposed_article_id;
            EXIT WHEN _proposed_article_id IS NULL;
            UPDATE articles
            SET type_article = 'PUBLISHED'
            WHERE id = _proposed_article_id;
        END LOOP;
        CLOSE cur_articles;
    ELSE
        UPDATE articles
        SET type_article = 'PUBLISHED'
        WHERE id = p_article_id;
    END IF;

    _article_id_to_update_numbers_proposals :=
            (SELECT article_id FROM proposed_articles WHERE proposed_article_id = p_article_id);

    IF _article_id_to_update_numbers_proposals IS NOT NULL THEN
        _numbers_proposals :=
                (SELECT numbers_proposals FROM articles WHERE id = _article_id_to_update_numbers_proposals);

        IF _numbers_proposals > 0 THEN
            _numbers_proposals := _numbers_proposals - 1;
        END IF;

        _article_id_to_update := _article_id_to_update_numbers_proposals;
        UPDATE articles
        SET numbers_proposals = _numbers_proposals
        WHERE id = _article_id_to_update;
    END IF;

END;
$$;

SELECT fn_update_articles_from_deleted('0efe4b9e-e301-40a9-bb3c-66877fc0af3a', FALSE);