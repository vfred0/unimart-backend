CREATE OR REPLACE FUNCTION fn_user_has_made_proposed(
    p_user_id UUID,
    p_proposed_article UUID)
    RETURNS BOOLEAN
AS
$$
DECLARE
    is_article_proposed            BOOLEAN;
    is_article_proposed_in_reverse BOOLEAN;
BEGIN
    is_article_proposed := EXISTS(SELECT 1
                                  FROM users u,
                                       articles a,
                                       proposed_articles pa
                                  WHERE u.id = p_user_id
                                    AND u.id = a.user_id
                                    AND a.id = pa.proposed_article_id
                                    AND pa.article_id = p_proposed_article);

    is_article_proposed_in_reverse := EXISTS(SELECT 1
                                             FROM users u,
                                                  articles a,
                                                  proposed_articles pa
                                             WHERE u.id = p_user_id
                                               AND u.id = a.user_id
                                               AND a.id = pa.article_id
                                               AND pa.proposed_article_id = p_proposed_article);
    RETURN is_article_proposed OR is_article_proposed_in_reverse;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION fn_update_type_article_from_deleted(p_article_id UUID) RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    _article_id     UUID;
    count_proposals SMALLINT;

BEGIN
    _article_id := CAST((SELECT pa.proposed_article_id
                         FROM articles a,
                              proposed_articles pa
                         WHERE a.id = p_article_id
                           AND a.id = pa.article_id) AS UUID);

    count_proposals := (SELECT COUNT(*)
                        FROM proposed_articles
                        WHERE proposed_article_id = _article_id);
    count_proposals := count_proposals - 1;

    IF count_proposals = 0 THEN
        UPDATE articles
        SET type_article = 'PUBLISHED'
        WHERE id = _article_id;
    END IF;
END;
$$;