CREATE OR REPLACE FUNCTION fn_is_user_receiver(p_user_id uuid, p_exchange_id uuid)
    RETURNS BOOLEAN
AS
$$
DECLARE
    v_receiver_user_id uuid;
BEGIN
    SELECT a.user_id
    INTO v_receiver_user_id
    FROM articles a
             JOIN proposed_articles pa ON a.id = pa.article_id
             JOIN exchanges e ON pa.id = e.proposed_article_id
    WHERE e.id = p_exchange_id;

    RETURN v_receiver_user_id = p_user_id;
END;
$$ LANGUAGE plpgsql;