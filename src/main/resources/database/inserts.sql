DO
$$
    DECLARE
        DECLARE
        user01_id            UUID := gen_random_uuid();
        DECLARE user02_id    UUID := gen_random_uuid();
        DECLARE user03_id    UUID := gen_random_uuid();
        DECLARE
        article01_id         UUID := gen_random_uuid();
        DECLARE article02_id UUID := gen_random_uuid();
        DECLARE article03_id UUID := gen_random_uuid();
    BEGIN
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user01_id, 'Descripcion user 01', 'user01', 'Usuario 1', 'https://is.gd/J7tWNO', 'user01', '123456789');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user02_id, 'Descripcion user 02', 'user02', 'Roxana Rocha', 'https://is.gd/G5qCMW', 'user02',
                '0987654321');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user03_id, 'Descripcion user 03', 'user03', 'Victor Arreaga', 'https://is.gd/DdAob3', 'user03',
                '0987655432');

        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article01_id, user01_id, 'Articulo 1', 'Descripcion del articulo 1', 'ELECTRONICS', 'NEW');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article02_id, user02_id, 'Articulo 2', 'Descripcion del articulo 2', 'ELECTRONICS', 'PREOWNED');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article03_id, user03_id, 'Articulo 3', 'Descripcion del articulo 3', 'ELECTRONICS', 'USED');

        INSERT INTO articles_images VALUES (article01_id, 'https://is.gd/DdAob3');
        INSERT INTO articles_images VALUES (article02_id, 'https://is.gd/DdAob3');
        INSERT INTO articles_images VALUES (article03_id, 'https://is.gd/DdAob3');

        INSERT INTO proposed_articles(id, article_id, proposed_article_id)
        VALUES (gen_random_uuid(), article01_id, article02_id);
        INSERT INTO proposed_articles(id, article_id, proposed_article_id)
        VALUES (gen_random_uuid(), article02_id, article03_id);
        INSERT INTO proposed_articles(id, article_id, proposed_article_id)
        VALUES (gen_random_uuid(), article03_id, article01_id);

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 1
        WHERE id IN (SELECT proposed_article_id FROM proposed_articles);

    END
$$;