CREATE OR REPLACE FUNCTION fn_get_rating_by_user_id(p_user_id uuid)
    RETURNS TABLE
            (
                user_name  VARCHAR(60),
                user_photo VARCHAR(50),
                date       TIMESTAMP,
                score      SMALLINT,
                comment    VARCHAR(250)
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT u.name,
               u.photo,
               r.date,
               r.score,
               r.comment
        FROM users u,
             ratings r
        WHERE u.id = p_user_id
          AND u.id = r.user_id;
END;
$$ LANGUAGE plpgsql;
