DO
$$
    DECLARE
        DECLARE
        user01_id            UUID := gen_random_uuid();
        DECLARE user02_id    UUID := gen_random_uuid();
        DECLARE user03_id    UUID := gen_random_uuid();
        DECLARE user04_id    UUID := gen_random_uuid();
        DECLARE user05_id    UUID := gen_random_uuid();
        DECLARE user06_id    UUID := gen_random_uuid();
        DECLARE
        article01_id         UUID := gen_random_uuid();
        DECLARE article02_id UUID := gen_random_uuid();
        DECLARE article03_id UUID := gen_random_uuid();
        DECLARE article04_id UUID := gen_random_uuid();
        DECLARE article05_id UUID := gen_random_uuid();
        DECLARE article06_id UUID := gen_random_uuid();
    BEGIN
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user01_id, 'Descripcion user 01', 'user01', 'Usuario 1', 'https://is.gd/J7tWNO', 'user01', '123456789');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user02_id, 'Descripcion user 02', 'user02', 'Roxana Rocha', 'https://is.gd/G5qCMW', 'user02',
                '0987654321');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user03_id, 'Descripcion user 03', 'user03', 'Victor Arreaga', 'https://is.gd/DdAob3', 'user03',
                '0987655432');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user04_id, 'Descripcion user 04', 'user04', 'Usuario 4', 'https://is.gd/5ZQX5U', 'user04',
                '0987655433');
        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user05_id, 'Descripcion user 05', 'user05', 'Usuario 5', 'https://is.gd/5ZQX5U', 'user05',
                '0987655434');

        INSERT INTO users (id, about, username, name, photo, password, number_whatsapp)
        VALUES (user06_id, 'Descripcion user 06', 'user06', 'Usuario 6', 'https://is.gd/5ZQX5U', 'user06',
                '0987655435');


        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article01_id, user01_id, 'Articulo 1', 'Descripcion del articulo 1', 'ELECTRONICS', 'NEW');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article02_id, user02_id, 'Articulo 2', 'Descripcion del articulo 2', 'ELECTRONICS', 'PREOWNED');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article03_id, user03_id, 'Articulo 3', 'Descripcion del articulo 3', 'ELECTRONICS', 'USED');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article04_id, user04_id, 'Articulo 4', 'Descripcion del articulo 4', 'ELECTRONICS', 'NEW');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article05_id, user05_id, 'Articulo 5', 'Descripcion del articulo 5', 'ELECTRONICS', 'PREOWNED');
        INSERT INTO articles (id, user_id, title, description, category, state)
        VALUES (article06_id, user06_id, 'Articulo 6', 'Descripcion del articulo 6', 'ELECTRONICS', 'USED');

        INSERT INTO article_images VALUES (article01_id, 'https://is.gd/DdAob3');
        INSERT INTO article_images VALUES (article02_id, 'https://is.gd/DdAob3');
        INSERT INTO article_images VALUES (article03_id, 'https://is.gd/DdAob3');
        INSERT INTO article_images VALUES (article04_id, 'https://is.gd/DdAob3');
        INSERT INTO article_images VALUES (article05_id, 'https://is.gd/DdAob3');
        INSERT INTO article_images VALUES (article06_id, 'https://is.gd/DdAob3');

        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article01_id, article02_id);
        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article02_id, article03_id);
        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article03_id, article01_id);
        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article01_id, article05_id);
        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article02_id, article06_id);
        INSERT INTO proposed_articles(id, receiver_article_id, proposer_article_id)
        VALUES (gen_random_uuid(), article06_id, article04_id);

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 2
        WHERE id = article01_id;

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 2
        WHERE id = article02_id;

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 1
        WHERE id = article03_id;

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 0
        WHERE id = article04_id;

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 0
        WHERE id = article05_id;

        UPDATE articles
        SET type_article      = 'PROPOSED',
            numbers_proposals = 1
        WHERE id = article06_id;


    END
$$;