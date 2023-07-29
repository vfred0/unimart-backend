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
                                    AND a.id = pa.article_id
                                    AND a.user_id = u.id
                                    AND pa.proposed_article_id = p_proposed_article);

    RAISE NOTICE 'is_article_proposed: %', is_article_proposed;

    is_article_proposed_in_reverse := EXISTS(SELECT 1
                                                FROM users u,
                                                     articles a,
                                                     proposed_articles pa
                                                WHERE u.id = p_user_id
                                                    AND a.id = pa.proposed_article_id
                                                    AND a.user_id = u.id
                                                    AND pa.article_id = p_proposed_article);
    RAISE NOTICE 'is_article_proposed_in_reverse: %', is_article_proposed_in_reverse;

RETURN is_article_proposed OR is_article_proposed_in_reverse;
END;
$$ LANGUAGE plpgsql;
