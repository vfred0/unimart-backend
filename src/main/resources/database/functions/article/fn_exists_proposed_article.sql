CREATE OR REPLACE FUNCTION fn_exists_proposed_article(p_article_id UUID) RETURNS boolean
    LANGUAGE plpgsql
AS
$$
BEGIN

    RETURN EXISTS((SELECT 1
                   FROM proposed_articles pa
                   WHERE pa.proposed_article_id = p_article_id
                     AND exchange_id IS NULL));
END;
$$;
