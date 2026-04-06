# Delivery App - Arquitectura de Microservicios

Sistema backend de delivery inspirado en plataformas como PedidosYa, desarrollado con Spring Boot y Spring Cloud bajo una arquitectura de microservicios.

El objetivo del proyecto es gestionar de forma desacoplada los distintos procesos de una aplicación de delivery, incluyendo usuarios, direcciones, locales, carrito, pedidos, pagos, repartidores y notificaciones.

---

## Arquitectura del proyecto

El sistema está compuesto por los siguientes microservicios:

- **api-gateway**: punto de entrada único al sistema
- **eureka-server**: servidor de descubrimiento de servicios
- **user-service**: gestión de usuarios
- **direccion-service**: gestión de direcciones
- **local-service**: gestión de locales/comercios
- **cart-service**: gestión del carrito de compras
- **order-service**: gestión de pedidos
- **payment-service**: procesamiento y administración de pagos
- **rider-service**: gestión de repartidores
- **notification-service**: envío de notificaciones

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Cloud
- Eureka Server
- Spring Cloud Gateway
- Maven
- REST APIs
- JPA / Hibernate
- Base de datos relacional
- Lombok

---

## Funcionalidades principales

- Registro y administración de usuarios
- Gestión de direcciones de entrega
- Administración de locales adheridos
- Carrito de compras
- Creación y seguimiento de pedidos
- Gestión de pagos
- Asignación y administración de riders
- Notificaciones del sistema
- Descubrimiento dinámico de servicios
- Enrutamiento centralizado mediante API Gateway
