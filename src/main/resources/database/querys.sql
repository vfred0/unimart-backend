DELETE FROM exchanges;
DELETE FROM proposals;
DELETE FROM articles;
DELETE FROM ratings;
DELETE FROM users;


select u.id, u.number_exchanges, u.rating,  u.name, u.username, a.id, a.title, a.category, a.type_article, a.numbers_proposals  from users u, articles a where u.id = a.user_id;
SELECT *
FROM proposals;

SELECT *
FROM exchanges;

SELECT *
FROM ratings;
