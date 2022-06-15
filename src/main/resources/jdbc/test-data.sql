

INSERT INTO customers (id, lastname, firstname, company, mail, mobile, notes, active) VALUES (1, 'JONES', 'Indiana', 'Université de Chicago', 'indiana.jonas@univ-chicago.com', '0666666666', 'Les notes d''Indiana', true);

INSERT INTO customers (id, lastname, firstname, company, mail, mobile, notes, active) VALUES (2, 'KENOBI', 'Obi-Wan', 'Jedis', 'obiwan.kenobi@jedis.com', '0666666666', 'Les notes d''Obi Wan', true);

INSERT INTO customers (id, lastname, firstname, company, mail, mobile, notes, active) VALUES (3, 'MCCLANE', 'John', 'NYPD', 'john.mcclane@nypd.com', '0666666666', 'Les notes de John', false);

INSERT INTO customers (id, lastname, firstname, company, mail, mobile, notes, active) VALUES (4, 'MCFLY', 'Marty', 'DOC', 'marty.mcfly@doc.com', NULL, 'Les notes de Marty', false);

INSERT INTO orders (id, customer_id, label, adr_et, number_of_days, tva, status, type, notes) VALUES (1, 1, 'Formation Java', 450.0, 5, 20, 'En cours', 'Forfait', 'Test');

INSERT INTO orders (id, customer_id, label, adr_et, number_of_days, tva, status, type, notes) VALUES (2, 1, 'Formation Spring', 450.0, 3, 20.0, 'En attente', 'Forfait', 'Test');

INSERT INTO orders (id, customer_id, label, adr_et, number_of_days, tva, status, type, notes) VALUES (3, 2, 'Formation Jedi', 1500.0, 2, 20.0, 'Payée', 'Forfait', 'Notes sur la formation');

INSERT INTO users (id, username, password, mail, grants) VALUES (1, 'toto', '1234', 'toto.titi@toto.com', 'ADMIN');