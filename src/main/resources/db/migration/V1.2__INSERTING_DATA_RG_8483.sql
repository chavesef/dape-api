INSERT INTO BET_STATUS (COD_BET_STATUS, DES_BET_STATUS) VALUES (1, 'PENDING');
INSERT INTO BET_STATUS (COD_BET_STATUS, DES_BET_STATUS) VALUES (2, 'GREEN');
INSERT INTO BET_STATUS (COD_BET_STATUS, DES_BET_STATUS) VALUES (3, 'RED');

INSERT INTO TICKET_STATUS (COD_TICKET_STATUS, DES_TICKET_STATUS) VALUES (1, 'PENDING');
INSERT INTO TICKET_STATUS (COD_TICKET_STATUS, DES_TICKET_STATUS) VALUES (2, 'GREEN');
INSERT INTO TICKET_STATUS (COD_TICKET_STATUS, DES_TICKET_STATUS) VALUES (3, 'RED');

INSERT INTO TICKET_TYPE (COD_TICKET_TYPE, DES_TICKET_TYPE) VALUES (1, 'SIMPLE');
INSERT INTO TICKET_TYPE (COD_TICKET_TYPE, DES_TICKET_TYPE) VALUES (2, 'MULTIPLE');

INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Ted Lasso', '123.456.789-00', 200.00, 'tlasso@richmond.com', '$2y$10$kfztPE0aYUXAloDkF8GPOOTOnOZzpsdLqP7impTS8jLPU8xa8lUzS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Rebecca Welton', '123.454.321-00', 1000.00, 'rwelton@richmond.com', '$2y$10$/6ZA5MTmx8qWF1.Vj0k.8OlGQMAXhS/NeAZEaD0of.iBcdq5C2yvW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Roy Kent', '987.654.321-00', 400.00, 'rkent@richmond.com', '$2y$10$DniBn2stkhullLSywyCz.ul6iTiE16H8quekauyHhjoRH5QvyYqNm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Coach Beard', '555.666.777-88', 2000.00, 'cbeard@richmond.com', '$2y$10$NzHksc70q0bHp1WOlw7O8.IRa3j3Uzgfh9JIBZo1HEKV97iezcjoy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Keeley Jones', '111.222.333-44', 900.00, 'kjones@richmond.com', '$2y$10$noAYVgSjeQwaxnQRRA1IKeObtWmoE4AhevFGt8ywuM7/Ws95WlsLW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Jamie Tartt', '999.888.777-66', 800.00, 'jtartt@richmond.com', '$2y$10$fTNpMFnT4tEy2Tw2utIZ3e3qhXFfKUy7cJdjxwAAtfhqEFvyv4sVu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Nathan Shelley', '121.343.565.77', 300.00, 'nshelley@richmond.com', '$2y$10$8Ecx9hlWtnvAxrU5NvrVieaEeGnSUL4ouyNEJVDdXMQh0/IOS..96', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Leslie Higgins', '989.767.545-33', 150.00, 'lhiggins@richmond.com', '$2y$10$jBD43pdhPX3YffwMp423qO6wx4Te3n8A4fadEK9EJ2aTRppa8l3ia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Sam Obisanya', '333.222.111-00', 450.00, 'sobisanya@richmond.com', '$2y$10$W.ezGgQZUUGkqs5PWWzzgeTXK2U2uM9uRXEQGBwLqcujJjv1Ev8ve', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLIENT (NAM_CLIENT, NUM_DOCUMENT, NUM_BALANCE, DES_EMAIL, NUM_PASSWORD, DAT_CREATED, DAT_UPDATED)
VALUES ('Dani Rojas', '989.898.989-11', 500.00, 'drojas@richmond.com', '$2y$10$XA6bPmPrBDa6yT3pn4tBOuyhDeApdNJ1BlE06VtGUR1qnZuJilYy6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO BET (NUM_ODD, FLG_SELECTED, DAT_CREATED, DAT_UPDATED, DES_BET, COD_BET_STATUS)
VALUES (2.13, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Vitória do São Paulo', 1);
INSERT INTO BET (NUM_ODD, FLG_SELECTED, DAT_CREATED, DAT_UPDATED, DES_BET, COD_BET_STATUS)
VALUES (1.32, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Vitória do Flamengo', 1);
INSERT INTO BET (NUM_ODD, FLG_SELECTED, DAT_CREATED, DAT_UPDATED, DES_BET, COD_BET_STATUS)
VALUES (1.45, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Vitória do Palmeiras', 1);