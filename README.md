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
├── reportes/           -> reportes de vulnerabilidades, correcciones y auditoría (PDF)
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
3. **Pruebas de seguridad**
   ```bash
   cd codigo-modificado/pedidos-api-seguro
   mvn test                    # PruebaBolaTest, PruebaMassAssignmentTest, PruebaValidacionTest
   ```
4. **Postman**: importar las colecciones de `postman/` de cada proyecto
   (la autenticación se simula con el encabezado `X-User-Id`).

## Resumen de hallazgos

_(se completa al finalizar la práctica — ver `reportes/`)_
