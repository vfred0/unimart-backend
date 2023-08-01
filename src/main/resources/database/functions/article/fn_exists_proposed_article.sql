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

select fn_exists_proposed_article('64844952-3a01-4a4e-8a88-6e0492c56aa6');
select fn_exists_proposed_article('3cd263d5-5e91-40c6-834f-3abb64fe0cf1');
