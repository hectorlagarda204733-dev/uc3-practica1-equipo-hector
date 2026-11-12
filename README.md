# uc3-practica1-equipo-hector

**Ciberseguridad Aplicada · Ingeniería en Software · ITSON**
**UC3: Seguridad de APIs y DevSecOps — Práctica 1: Seguridad en APIs (BOLA · Mass Assignment · Validación estricta)**

## Integrantes

| Nombre | Matrícula | Rol |
|---|---|---|
| Hector Andres Lagarda Alcantar | 204733 | Explotación, corrección, documentación y QA |

## Estructura del repositorio

```
├── codigo-original/    -> pedidos-api-vulnerable (BOLA, Mass Assignment, datos expuestos)
├── codigo-modificado/  -> pedidos-api-seguro (controles aplicados + pruebas de seguridad)
├── evidencias/         -> capturas de explotación, retest y bitácora del equipo
│   ├── explotacion/    -> ataques ANTES (Postman): 00 a 06
│   ├── validacion/     -> retest DESPUÉS (Postman + NetBeans): 00 a 09
│   └── bitacora.md     -> quién hizo qué, con fecha y hora
├── reportes/           -> reporte de vulnerabilidades, correcciones y auditoría (.md y .pdf)
└── README.md
```

## Cómo ejecutar

1. **API vulnerable**
   ```bash
   cd codigo-original/pedidos-api-vulnerable
   mvn spring-boot:run        # http://localhost:8080  (datos de prueba: Ana id=1, Beto id=2)
   ```
2. **API segura**
   ```bash
   cd codigo-modificado/pedidos-api-seguro
   mvn spring-boot:run
   ```
3. **Pruebas de seguridad** (o en NetBeans: clic derecho al proyecto → Test Project)
   ```bash
   cd codigo-modificado/pedidos-api-seguro
   mvn test                    # PruebaBolaTest, PruebaMassAssignmentTest, PruebaValidacionTest (9 en verde)
   ```
4. **Postman**: importar las colecciones de `postman/` de cada proyecto
   (la autenticación se simula con el encabezado `X-User-Id`).

> ⚠️ Si el puerto 8080 está ocupado, usar `--server.port=8081` (o `-Dspring-boot.run.arguments=--server.port=8081`)
> y ajustar la variable `{{baseUrl}}` en Postman. Así se ejecutó durante esta práctica.

## Resumen de hallazgos

| ID | Vulnerabilidad | Severidad | OWASP | Estado |
|---|---|---|---|---|
| P1-001 | BOLA de lectura en `GET /api/pedidos/{id}` (200 con pedido ajeno) | Crítica | API1:2023 | Corregido → 403/401 |
| P1-002 | BOLA de escritura en `POST /api/pedidos` (dueño desde el JSON) | Crítica | API1:2023 | Corregido → dueño = `X-User-Id` |
| P1-003 | Mass Assignment en `POST /api/usuarios` (`"rol":"ADMIN"` guardado) | Crítica | API3:2023 | Corregido → rol fijo USER |
| P1-004 | Contraseñas expuestas en respuestas JSON | Alta | API3/API8:2023 | Corregido → DTOs + `@JsonIgnore` |
| P1-005 | Validación inadecuada de esquemas (montos extremos, emails inválidos) | Alta | API8:2023 | Corregido → `@Valid` + 400 por campo |

**Verificación:** `mvn test` → `Tests run: 9, Failures: 0` (NetBeans: *9 tests passed*) + retest en Postman (403/401/400, rol USER).

Detalle completo: `reportes/` (PDF y Markdown) y `evidencias/bitacora.md`.
