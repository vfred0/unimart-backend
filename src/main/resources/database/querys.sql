DELETE FROM exchanges;
DELETE FROM proposed_articles;
DELETE FROM articles_images;
DELETE FROM articles;
DELETE FROM users;
DELETE FROM ratings;


select u.id, u.name, u.username, a.id, a.title, a.category, a.type_article, a.numbers_proposals  from users u, articles a where u.id = a.user_id;
SELECT *
FROM proposed_articles;

SELECT *
FROM exchanges;

SELECT *
FROM ratings;
