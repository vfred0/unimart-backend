INSERT INTO roles(id, authority)
VALUES (gen_random_uuid(), 'ADMIN'),
       (gen_random_uuid(), 'AUTHENTICATED');
