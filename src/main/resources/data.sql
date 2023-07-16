-- DEFAULT MENU
-- Insert into option_menu table
INSERT INTO option_menu (id, name, enabled) VALUES (1, 'default', true);

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
