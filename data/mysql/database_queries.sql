DROP DATABASE IF EXISTS floristeria;
CREATE DATABASE IF NOT EXISTS floristeria;
USE floristeria;

CREATE TABLE IF NOT EXISTS tendes(
	id		TINYINT UNSIGNED PRIMARY KEY,
    nom		VARCHAR(50)
);

-- sólo tenemos que meter una
INSERT INTO tendes
VALUES(1, "Floristeria MYSQL");

CREATE TABLE IF NOT EXISTS item_tipus(
	id		TINYINT UNSIGNED PRIMARY KEY,
    nom		VARCHAR(30)
);

INSERT INTO item_tipus
VALUES(1, "Arbre"), (2, "Decoracio"), (3, "Flor");

CREATE TABLE IF NOT EXISTS items(
	id			SERIAL PRIMARY KEY,
    nom			VARCHAR(60),
    tipus_id	TINYINT UNSIGNED,
    alcada		LONG DEFAULT NULL,
    material 	VARCHAR(20) DEFAULT NULL,
    color		VARCHAR(30) DEFAULT NULL
);

-- metemos algunos items
INSERT INTO items (nom, tipus_id, alcada, material, color)
VALUES("arbre_sql_1", 1, 2.54, null, null), ("arbre_sql_2", 1, 3.54, null, null),
	  ("dec_sql_1", 2, null, "Plastic", null), ("dec_sql_2", 2, null, "Fusta", null),
      ("flor_sql_1", 3, null, null, "blau"), ("flor_sql_2", 3, null, null, "vermell");


-- al principio tenia tanto la floris como el item como primary key, pero como
-- esto sólo usará una floristeria nos lo podemos ahorrar.
CREATE TABLE IF NOT EXISTS stock(
	# id_floristeria	BIGINT UNSIGNED,
    id_item			BIGINT UNSIGNED,
    preu			LONG,
    quantitat		INTEGER,
    # PRIMARY KEY(id_floristeria, id_item),
    PRIMARY KEY (id_item),
    # FOREIGN KEY (id_floristeria) REFERENCES tendes(id),
    FOREIGN KEY (id_item) REFERENCES items(id)
);

INSERT INTO stock
VALUES (1, 1.54, 1), (2, 2.54, 4), (3, 2.65, 7), (4, 7.45, 1), (5, 21.01, 25), (6, 12.24, 8); 

CREATE TABLE IF NOT EXISTS tickets(
	id			BIGINT UNSIGNED PRIMARY KEY
);

INSERT INTO tickets
VALUES (1), (2), (3);

CREATE TABLE IF NOT EXISTS ticket_items(
	id_ticket 	BIGINT UNSIGNED,
    id_item		BIGINT UNSIGNED,
    preu		LONG,
    quantitat	INT,
    PRIMARY KEY (id_ticket, id_item),
    FOREIGN KEY (id_ticket) REFERENCES tickets(id),
    FOREIGN KEY (id_item) REFERENCES items(id)
);

INSERT INTO ticket_items
VALUES (1, 1, 1.54, 3), (1, 2, 2.56, 1),
	   (2, 1, 7.24, 2), (2, 6, 1.51, 2), (2, 4, 1.78, 2),
       (3, 4, 2.14, 25);
       
-- DELETE FROM ticket_items;

-- esta es la vista con la que cargaremos los datos de la floristeria
CREATE OR REPLACE VIEW vw_stock AS
SELECT item_tipus.nom AS "Tipus", items.nom, items.alcada, items.material, items.color, stock.preu, stock.quantitat FROM items
JOIN stock ON stock.id_item = items.id
JOIN item_tipus ON items.tipus_id = item_tipus.id;

-- esta es la vista con la que cargaremos los tickets
CREATE OR REPLACE VIEW vw_ticket AS
SELECT item_tipus.nom AS "Tipus", items.nom, items.alcada, items.material, items.color, ticket_items.preu, ticket_items.quantitat, ticket_items.id_ticket FROM ticket_items
JOIN items ON id_item = items.id
JOIN item_tipus ON items.tipus_id = item_tipus.id
ORDER BY id_ticket;

SELECT * FROM vw_ticket;

SELECT * FROM vw_stock;

-- con esto puedo pillar el id
SELECT id FROM items
WHERE items.nom LIKE "arbre_sql_1";


SELECT * FROM items;




