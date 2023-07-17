-- DEFAULT MENU
-- Insert into option_menu table
INSERT INTO option_menu (id, name, enabled) VALUES
    (1, 'default', true);

-- Insert into option table for each option
INSERT INTO option (id, name, icon, url, _order, option_menu_id) VALUES
    (1, 'Inicio', 'home', '/inicio', 1, 1),
    (2, 'Tarjetas', 'credit_card', '/tarjetas', 2, 1),
    (3, 'Prestamos', 'cash', '/prestamos', 3, 1),
    (4, 'Operaciones', 'exchange', '/operaciones', 4, 1),
    (5, 'Te ofrecemos', 'gift', '/ofertas', 5, 1),
    (6, 'Seguros', 'shield', '/seguros', 6, 1),
    (7, 'Puntos', 'loyalty', '/puntos', 7, 1),
    (8, 'Ayuda', 'help', '/ayuda', 8, 1),
    (9, 'Cerrar sesión', 'logout', '/logout', 9, 1);

-- Insert into option_role table for each option
INSERT INTO option_role (option_id, roles)
SELECT o.id, 'USER' AS roles
FROM option AS o;

-- USER
INSERT INTO _user (id, account_non_expired, account_non_locked, created_at, credentials_non_expired, dni, document_type, email, enabled, first_name, last_name, password) VALUES
    (1, true, true, '2023-07-15 18:34:25.303452', true, '43133332', 0, 'zeballosleonel3@gmail.com', true, 'Leonel', 'Zeballos', 'test123');

-- ACCOUNT TYPES
INSERT INTO account_type (id, description, name) VALUES
    (1, 'Savings Account', 'Savings'),
    (2, 'Checking Account', 'Checking'),
    (3, 'Credit Card', 'Credit Card'),
    (4, 'Loan Account', 'Loan');

-- ACCOUNT FOR USER_ID 1
INSERT INTO account (id, account_number, balance, created_at, account_type_id, user_id) VALUES
    (1, '123456789', 1000.00, NOW(), 1, 1);

-- CARD FOR ACCOUNT_ID 1
INSERT INTO card (card_type, id, card_number, cvv, expiration_date, credit_limit, account_id, owner_id, titular)
VALUES
    ('DEBIT_CARD', 1, '1234567890123456', '123', '2025-12-31', 5000.00, 1, 1, 'Leonel Ayrton Zeballos');
