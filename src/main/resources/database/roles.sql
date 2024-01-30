INSERT INTO identity.roles(id, name)
VALUES (gen_random_uuid(), 'ROLE_ADMIN'),
       (gen_random_uuid(), 'ROLE_AUTHENTICATED');

SELECT * FROM identity.roles;