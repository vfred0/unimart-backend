CREATE OR REPLACE PROCEDURE proc_delete_other_proposed_articles(
    p_proposed_article_id uuid,
    p_receiver_article_id uuid,
    p_proposer_article_id uuid
)
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM proposed_articles
    WHERE id <> p_proposed_article_id
      AND (receiver_article_id IN (p_receiver_article_id, p_proposer_article_id)
        OR proposer_article_id IN (p_receiver_article_id, p_proposer_article_id));
END;
$$;

CALL proc_delete_other_proposed_articles('6db7f447-95ad-4c53-88d8-e212a506de67', 'c85635ac-ea77-4bbd-8849-d7a6750ba90d', '194649b2-ceeb-40a5-bbf3-a7797e0a24f3')