# Bitácora del equipo — Práctica 1 UC3 (Seguridad en APIs)

**Equipo:** Equipo Hector — Hector Andres Lagarda Alcantar (Matrícula 204733)
**Repo:** https://github.com/hectorlagarda204733-dev/uc3-practica1-equipo-hector
**Nota:** trabajo unipersonal; todos los pasos fueron ejecutados y revisados por el integrante único.

| # | Fecha | Hora | Actividad | Responsable |
|---|---|---|---|---|
| 1 | 2026-11-11 | 19:45 | Creación del repositorio GitHub `uc3-practica1-equipo-hector` y carga de la estructura base (codigo-original, codigo-modificado, evidencias, reportes, README). | Hector Andres Lagarda Alcantar (204733) |
| 2 | 2026-11-11 | 19:50 | Ejecución de la API **vulnerable** (`pedidos-api-vulnerable`). Se usó el puerto **8081** porque el 8080 estaba ocupado por GeoServer (guía: cambiar puerto). | Hector Andres Lagarda Alcantar (204733) |
| 3 | 2026-11-11 | 20:05 | Importación en Postman de `Coleccion_Explotacion_P1.json` (variable `baseUrl` = http://localhost:8081) y ejecución de las peticiones 00–03: exposición de datos y BOLA de lectura confirmadas (200 con datos ajenos). | Hector Andres Lagarda Alcantar (204733) |
| 4 | 2026-11-11 | 20:14 | **Ajuste de entorno documentado:** en ambos `data.sql` se quitaron los IDs explícitos de los INSERT. Causa: H2 2.2.224 no avanza la secuencia IDENTITY al insertar IDs explícitos, por lo que todo POST chocaba con la clave primaria (500). Con IDs automáticos la identidad genera 1,2,3 (mismos datos, sin cambio funcional; compatible también con MySQL). | Hector Andres Lagarda Alcantar (204733) |
| 5 | 2026-11-11 | 20:24 | Ejecución de peticiones 04–06 (BOLA de escritura, Mass Assignment, verificación de rol ADMIN) con BD limpia. Resultados: 200/201, pedido a nombre de Beto y usuario con rol ADMIN. Evidencias capturadas. | Hector Andres Lagarda Alcantar (204733) |
| 6 | 2026-11-11 | 20:30 | Redacción del Reporte de Vulnerabilidades con la plantilla oficial (hallazgos P1-001 a P1-005). | Hector Andres Lagarda Alcantar (204733) |
| 7 | 2026-11-11 | 20:45 | Ejecución de la API **segura** y retest con `Coleccion_Validacion_P1.json` (403/401/400, rol ignorado). | Hector Andres Lagarda Alcantar (204733) |
| 8 | 2026-11-11 | 21:00 | Ejecución de las pruebas de seguridad (`mvn test`): PruebaBolaTest, PruebaMassAssignmentTest, PruebaValidacionTest en verde. | Hector Andres Lagarda Alcantar (204733) |
| 9 | 2026-11-11 | 21:30 | Redacción de Reporte de Correcciones y Reporte de Auditoría; exportación a PDF. | Hector Andres Lagarda Alcantar (204733) |
| 10 | 2026-11-11 | 22:00 | Revisión final, commit y push de reportes + evidencias al repositorio. | Hector Andres Lagarda Alcantar (204733) |
