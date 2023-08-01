CREATE OR REPLACE FUNCTION fn_exists_proposed_article(p_article_id UUID) RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN

    RETURN EXISTS((SELECT 1
                   FROM proposed_articles pa
                   WHERE pa.proposed_article_id = p_article_id
                     AND id NOT IN
                         (SELECT e.proposed_article_id
                          FROM exchanges e
                          WHERE e.proposed_article_id = pa.proposed_article_id)));
END;
$$;
