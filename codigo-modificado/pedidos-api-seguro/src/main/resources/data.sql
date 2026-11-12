-- =====================================================================
-- Practica 1 - Datos iniciales (version SEGURA)
-- Mismos datos que la version vulnerable para comparar resultados:
--   * Usuario 1 (Ana): pedidos 1 y 2
--   * Usuario 2 (Beto): pedido 3
-- =====================================================================

INSERT INTO usuarios (id, nombre, email, password, rol) VALUES
  (1, 'Ana', 'ana@itson.mx', 'ana12345', 'USER'),
  (2, 'Beto', 'beto@itson.mx', 'beto12345', 'USER');

INSERT INTO pedidos (id, descripcion, monto, fecha, usuario_id) VALUES
  (1, 'Laptop para laboratorio', 18500.00, '2026-09-01', 1),
  (2, 'Mouse inalambrico', 450.00, '2026-09-15', 1),
  (3, 'Monitor 24 pulgadas', 3200.00, '2026-10-02', 2);
