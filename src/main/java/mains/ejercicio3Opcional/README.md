# API de Estadísticas de Ventas

## Descripción
API REST para registrar ventas y calcular estadísticas con filtros avanzados por fecha y producto.

## Arquitectura
**Patrón**: Model-Service-Repository-Controller (MSRC)

### Estructura del Proyecto
```
ejercicio3Opcional/
├── controller/              # Controladores HTTP
│   └── VentaController.java
├── services/               # Lógica de negocio
│   └── VentaService.java
├── repositories/           # Acceso a datos
│   └── VentaRepository.java
├── models/                 # Modelos de datos
│   └── Venta.java
├── dto/                    # Data Transfer Objects
│   ├── VentaRequest.java
│   └── EstadisticasResponse.java
└── EstadisticasAPI.java   # Punto de entrada
```

## Componentes

### Models
- **Venta**: Entidad que representa una venta
  - `id`: Identificador único (generado automáticamente)
  - `producto`: Nombre del producto vendido
  - `cantidad`: Cantidad de unidades vendidas
  - `precioUnitario`: Precio por unidad
  - `fecha`: Fecha y hora de la venta (automática)
  - `total`: Calculado (cantidad × precioUnitario)

### DTOs
- **VentaRequest**: Objeto para registrar nuevas ventas
  - `producto`: Nombre del producto
  - `cantidad`: Cantidad vendida
  - `precioUnitario`: Precio unitario

- **EstadisticasResponse**: Respuesta con estadísticas calculadas
  - `totalVentas`: Suma total de ventas
  - `numeroTransacciones`: Cantidad de ventas registradas
  - `productoMasVendido`: Producto con mayor cantidad vendida
  - `ventaPromedio`: Promedio de venta por transacción
  - `cantidadTotalProductos`: Total de unidades vendidas

### Repositories
- **VentaRepository**: Capa de acceso a datos
  - `save()`: Guarda una nueva venta
  - `findById()`: Busca venta por ID
  - `findAll()`: Obtiene todas las ventas
  - `findByProducto()`: Filtra por producto
  - `findByFechaRango()`: Filtra por rango de fechas
  - `findByProductoAndFechaRango()`: Filtro combinado

### Services
- **VentaService**: Lógica de negocio
  - Validaciones de datos de entrada
  - Registro de ventas
  - Cálculo de estadísticas
  - Filtrado por producto y fecha

### Controllers
- **VentaController**: Maneja las peticiones HTTP
  - Conversión de requests a objetos
  - Manejo de errores
  - Formateo de respuestas JSON

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/ventas` | Registrar nueva venta |
| GET | `/ventas` | Obtener todas las ventas |
| GET | `/ventas?producto=X` | Filtrar ventas por producto |
| GET | `/ventas/{id}` | Obtener una venta específica |
| GET | `/estadisticas` | Obtener estadísticas generales |
| GET | `/estadisticas?fecha_inicio=YYYY-MM-DD&fecha_fin=YYYY-MM-DD` | Estadísticas por rango de fechas |

## Configuración
- **Puerto**: 7070
- **Framework**: Javalin
- **Content-Type**: application/json

## Validaciones

### Registro de Ventas
- Producto obligatorio (no vacío)
- Cantidad mayor a 0
- Precio unitario mayor a 0

## Funcionalidades Avanzadas

### Filtros
1. **Por Producto**: Obtiene todas las ventas de un producto específico
2. **Por Rango de Fechas**: Filtra ventas entre dos fechas
3. **Combinado**: Producto y rango de fechas simultáneamente

### Estadísticas Calculadas
- **Total de Ventas**: Suma de todos los totales
- **Número de Transacciones**: Cantidad de ventas realizadas
- **Producto Más Vendido**: Por cantidad de unidades
- **Venta Promedio**: Total ventas / Número transacciones
- **Cantidad Total**: Suma de todas las unidades vendidas

## Manejo de Errores
- 201: Venta registrada exitosamente
- 200: Consulta exitosa
- 400: Datos inválidos o formato de fecha incorrecto
- 404: Venta no encontrada
- 500: Error al calcular estadísticas

## Almacenamiento
- **Tipo**: En memoria (HashMap)
- **Persistencia**: No persistente (se pierde al reiniciar)
- **ID**: Auto-incrementado secuencial

## Ejemplo de Uso

### Registrar una venta
```bash
POST http://localhost:7070/ventas
Content-Type: application/json

{
  "producto": "Laptop",
  "cantidad": 2,
  "precioUnitario": 850.50
}
```

**Respuesta:**
```json
{
  "mensaje": "Venta registrada exitosamente",
  "id": "1",
  "total": 1701.0
}
```

### Obtener ventas de un producto
```bash
GET http://localhost:7070/ventas?producto=Laptop
```

### Obtener estadísticas generales
```bash
GET http://localhost:7070/estadisticas
```

**Respuesta:**
```json
{
  "totalVentas": 5430.75,
  "numeroTransacciones": 8,
  "productoMasVendido": "Laptop",
  "ventaPromedio": 678.84,
  "cantidadTotalProductos": 25
}
```

### Obtener estadísticas por rango de fechas
```bash
GET http://localhost:7070/estadisticas?fecha_inicio=2025-01-01&fecha_fin=2025-12-31
```

## Formato de Fechas
- **Entrada**: `YYYY-MM-DD` (ejemplo: 2025-12-04)
- **Salida**: `yyyy-MM-dd'T'HH:mm:ss` (ejemplo: 2025-12-04T15:30:45)

## Casos de Uso
1. **E-commerce**: Registrar y analizar ventas de productos
2. **Inventario**: Tracking de productos más vendidos
3. **Reportes**: Generar estadísticas por período
4. **Analytics**: Análisis de tendencias de venta

## Dependency Injection
```java
VentaRepository → VentaService → VentaController
```
Patrón de inyección de dependencias manual para mejor testabilidad.

## Ejecutar
```java
// Ejecutar desde: EstadisticasAPI.java
public static void main(String[] args) {
    // La aplicación inicia en http://localhost:7070
}
```

## Mejoras Futuras
- [ ] Persistencia en base de datos
- [ ] Autenticación y autorización
- [ ] Paginación de resultados
- [ ] Exportación de reportes (CSV, PDF)
- [ ] WebSocket para estadísticas en tiempo real
