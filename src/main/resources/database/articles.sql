CREATE OR REPLACE FUNCTION get_random_user_id() RETURNS UUID AS
$$
DECLARE
    user_id UUID;

BEGIN
    SELECT id INTO user_id FROM users ORDER BY RANDOM() LIMIT 1;
    RETURN user_id;
END;
$$ LANGUAGE plpgsql;

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Libro de Matemáticas', 'Libro usado de matemáticas', 'TEXT_BOOKS_EDUCATIONAL_MATERIAL',
        'USED', '{"https://is.gd/u8Vva0", "https://is.gd/wb1Ecp"}');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Libro de Historia', 'Libro nuevo de historia universal',
        'TEXT_BOOKS_EDUCATIONAL_MATERIAL', 'NEW', '{"https://is.gd/u8Vva0", "https://is.gd/wb1Ecp"}');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Libro de Matemáticas Avanzadas', 'Libro de matemáticas avanzadas usado',
        'TEXT_BOOKS_EDUCATIONAL_MATERIAL',
        'USED', '{"https://is.gd/u8Vva0", "https://is.gd/wb1Ecp"}');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Libro de Álgebra', 'Libro de álgebra nuevo', 'TEXT_BOOKS_EDUCATIONAL_MATERIAL', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Libro de Trigonometría', 'Libro de trigonometría usado',
        'TEXT_BOOKS_EDUCATIONAL_MATERIAL', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Resmas de hojas', 'Paquete de 10 resmas de hojas', 'OFFICE_SUPPLIES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Lapiceras azules', 'Paquete de 20 lapiceras azules', 'OFFICE_SUPPLIES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Sacapuntas eléctrico', 'Sacapuntas eléctrico usado', 'OFFICE_SUPPLIES', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Cuadernos universitarios', 'Paquete de cuadernos universitarios rayados',
        'OFFICE_SUPPLIES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Marcadores Sharpie', 'Paquete de marcadores Sharpie surtidos', 'OFFICE_SUPPLIES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Lapiceras rojas', 'Paquete de lapiceras rojas', 'OFFICE_SUPPLIES', 'NEW');


INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'iPad Pro 11"', 'iPad Pro 11" usado', 'ELECTRONICS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Laptop Lenovo', 'Laptop Lenovo nueva', 'ELECTRONICS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'iMac 27"', 'iMac 27" usado', 'ELECTRONICS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'iMac 21"', 'iMac 21" nuevo', 'ELECTRONICS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'MacBook Pro 16"', 'MacBook Pro 16" usado', 'ELECTRONICS', 'USED');



INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Silla de escritorio', 'Silla de escritorio para oficina', 'FURNITURE_AND_DECORATION',
        'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Cuadro decorativo', 'Cuadro decorativo para sala', 'FURNITURE_AND_DECORATION', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Mesa de centro', 'Mesa de centro de vidrio para sala', 'FURNITURE_AND_DECORATION',
        'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Silla ejecutiva', 'Silla ejecutiva de oficina', 'FURNITURE_AND_DECORATION', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Silla de visitas', 'Silla de visitas para sala', 'FURNITURE_AND_DECORATION', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Silla para barra desayunador', 'Silla para barra de desayunador',
        'FURNITURE_AND_DECORATION', 'NEW');



INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Vestido de fiesta', 'Vestido de fiesta para mujer', 'CLOTHING', 'NEW', 'FEMALE');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Traje para hombre', 'Traje azul para hombre', 'CLOTHING', 'USED', 'MALE');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Zapatillas Adidas', 'Zapatillas deportivas Adidas para hombre', 'CLOTHING', 'NEW',
        'MALE');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Traje sastre mujer', 'Traje sastre para mujer', 'CLOTHING', 'NEW', 'FEMALE');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Traje chaqueta hombre', 'Traje chaqueta para hombre', 'CLOTHING', 'USED', 'MALE');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Traje baño mujer', 'Traje de baño para mujer', 'CLOTHING', 'NEW', 'FEMALE');



INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Balanza analítica', 'Balanza analítica de precisión', 'LABORATORY_MATERIAL', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Tubos de ensayo', 'Paquete de 50 tubos de ensayo nuevos', 'LABORATORY_MATERIAL', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Microscopio', 'Microscopio de laboratorio usado', 'LABORATORY_MATERIAL', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Centrífuga para laboratorio', 'Centrífuga para laboratorio', 'LABORATORY_MATERIAL',
        'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Matraz erlenmeyer', 'Matraz erlenmeyer de vidrio nuevo', 'LABORATORY_MATERIAL', 'NEW');



INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Guitarra eléctrica', 'Guitarra eléctrica usada en buen estado', 'MUSICAL_INSTRUMENTS',
        'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Saxofón', 'Saxofón para estudiante nuevo', 'MUSICAL_INSTRUMENTS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Flauta traversa', 'Flauta traversa usada para estudiante', 'MUSICAL_INSTRUMENTS',
        'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Piano eléctrico', 'Piano eléctrico usado', 'MUSICAL_INSTRUMENTS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Batería acústica', 'Batería acústica 5 piezas', 'MUSICAL_INSTRUMENTS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Ukulele', 'Ukulele nuevo para principiantes', 'MUSICAL_INSTRUMENTS', 'NEW');



INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Pelota de fútbol', 'Pelota de fútbol oficial nueva', 'SPORTING_GOODS', 'NEW', 'UNISEX');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Pesas de gimnasio', 'Juego de pesas de gimnasio usadas', 'SPORTING_GOODS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Bicicleta MTB', 'Bicicleta MTB suspension completa', 'SPORTING_GOODS', 'NEW', 'UNISEX');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Patineta', 'Patineta profesional usada', 'SPORTING_GOODS', 'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Set de pesas', 'Set de pesas y barras para gimnasio', 'SPORTING_GOODS', 'USED');



INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Set de óleo pintura', 'Set de pinturas al óleo nuevo', 'ARTS_HANDICRAFTS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Ganchillo', 'Kit de ganchillo con lana y agujas', 'ARTS_HANDICRAFTS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Set de acuarelas', 'Set de acuarelas con pinceles', 'ARTS_HANDICRAFTS', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Bastidor para óleo', 'Bastidor de madera para pintura al óleo', 'ARTS_HANDICRAFTS',
        'USED');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Ganchillo tunecino', 'Kit de ganchillo tunecino con lana', 'ARTS_HANDICRAFTS', 'NEW');


INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Muñeca Barbie', 'Muñeca Barbie nueva', 'TOYS_GAMES', 'NEW', 'FEMALE');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Rompecabezas 1000 piezas', 'Rompecabezas nuevo de 1000 piezas', 'TOYS_GAMES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Juego de química', 'Juego de química para niños', 'TOYS_GAMES', 'USED', 'UNISEX');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Rompecabezas 2000 piezas', 'Rompecabezas nuevo de 2000 piezas', 'TOYS_GAMES', 'NEW');

INSERT INTO articles (user_id, title, description, category, state, gender, images)
VALUES (get_random_user_id(), 'Rompecabezas 3000 piezas', 'Rompecabezas de 3000 piezas', 'TOYS_GAMES', 'NEW', 'UNISEX');

INSERT INTO articles (user_id, title, description, category, state, images)
VALUES (get_random_user_id(), 'Rompecabezas 5000 piezas', 'Rompecabezas nuevo de 5000 piezas', 'TOYS_GAMES', 'NEW');