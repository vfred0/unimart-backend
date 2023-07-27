CREATE OR REPLACE FUNCTION fn_user_has_made_proposed(
    p_user_id UUID,
    p_proposed_article UUID)
    RETURNS BOOLEAN
AS
$$
BEGIN
    RETURN EXISTS(SELECT 1
                  FROM users u,
                       articles a,
                       proposed_articles pa
                  WHERE u.id = p_user_id
                    AND a.id = pa.proposed_article_id
                    AND pa.article_id = p_proposed_article);
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS fn_user_has_made_proposed_changes(p_user_id UUID, p_proposed_article UUID)