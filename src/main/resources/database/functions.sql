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
    is_proposed     BOOLEAN;
BEGIN
    _article_id := CAST((SELECT pa.proposed_article_id
                         FROM articles a,
                              proposed_articles pa
                         WHERE a.id = p_article_id
                           AND a.id = pa.article_id) AS UUID);


    IF _article_id IS NULL THEN
        is_proposed := TRUE;
        _article_id := CAST((SELECT pa.article_id
                             FROM articles a,
                                  proposed_articles pa
                             WHERE a.id = p_article_id
                               AND a.id = pa.proposed_article_id) AS UUID);

        count_proposals := (SELECT COUNT(*)
                            FROM proposed_articles
                            WHERE article_id = _article_id);
    ELSE
        count_proposals := (SELECT COUNT(*)
                            FROM proposed_articles
                            WHERE proposed_article_id = _article_id);
    END IF;
    RAISE NOTICE 'article_id: %', _article_id;
    RAISE NOTICE 'count_proposals: %', count_proposals;
    count_proposals := count_proposals - 1;
    RAISE NOTICE 'count_proposals: %', count_proposals;

    IF count_proposals = 0 THEN
        IF is_proposed THEN
            _article_id := p_article_id;
        END IF;
        UPDATE articles
        SET type_article = 'PUBLISHED'
        WHERE id = _article_id;
    END IF;
END;
$$;

SELECT fn_update_type_article_from_deleted('839907cf-72d3-4754-8c9d-57a8ff79d22b')
SELECT fn_update_type_article_from_deleted('0cb991f7-3e47-470b-8328-fd132304f818')



CREATE OR REPLACE FUNCTION fn_exists_proposed_article(p_article_id UUID) RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN

    RETURN EXISTS((SELECT 1
                   FROM proposed_articles pa
                   WHERE pa.proposed_article_id = p_article_id));
END;
$$;


SELECT a.id
FROM articles a,
     proposed_articles pa
WHERE pa.article_id = a.id
  AND pa.proposed_article_id = '839907cf-72d3-4754-8c9d-57a8ff79d22b'