-- =====================================================================
-- Practica 1 - Datos iniciales (version VULNERABLE)
-- 2 usuarios y 3 pedidos con IDs secuenciales para explotar BOLA:
--   * Usuario 1 (Ana): pedidos 1 y 2
--   * Usuario 2 (Beto): pedido 3
-- =====================================================================

INSERT INTO usuarios (nombre, email, password, rol) VALUES
  ('Ana', 'ana@itson.mx', 'ana12345', 'USER'),
  ('Beto', 'beto@itson.mx', 'beto12345', 'USER');

INSERT INTO pedidos (descripcion, monto, fecha, usuario_id) VALUES
  ('Laptop para laboratorio', 18500.00, '2026-09-01', 1),
  ('Mouse inalambrico', 450.00, '2026-09-15', 1),
  ('Monitor 24 pulgadas', 3200.00, '2026-10-02', 2);
