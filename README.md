### Business Customer V1
`<autor>`: Miguel Rodrigo Armas Abt

## Acerca de la funcionalidad

| Endpoint                                    | Método | Descripción                                                              |
|---------------------------------------------|--------|--------------------------------------------------------------------------|
| `/v1/customers`                             | GET    | Recupera todos una lista con todos los clientes                          |
| `/v1/customers?documentType={documentType}` | GET    | Recupera una lista con todos los clientes filtrado por tipo de documento |
| `/v1/customers/{uniqueCode}`                | GET    | Recupera un cliente por uniqueCode                                       |
| `/v1/customers/{uniqueCode}`                | PUT    | Actualiza un registro de cliente por uniqueCode                          |
| `/v1/customers`                             | POST   | Crea un nuevo cliente                                                    |
| `/v1/customers/{uniqueCode}`                | DELETE | Elimina un cliente                                                       |

> Observación: Todos los endpoints requieren el header Authorization.

## Prerrequisitos para instalación y despliegue
* Java 11
* Maven 3.8.1
* Habilitar plugin de Lombok
* Docker

## Despliegue con Docker
> Nota: Los siguientes comandos deben ser ejecutados en la raíz del proyecto.

| Procedimiento          | Comando                                                                         |
|------------------------|---------------------------------------------------------------------------------|
| Generar el .jar        | `mvn clean install`                                                             |
| Crear imagen           | `docker build -t busines-customer-v1:0.0.1 .`                                   |
| Ver imágenes           | `docker image ls`                                                               |
| Ejecutar la aplicación | `docker run --name busines-customer-v1 -p 8093:8093 busines-customer-v1:0.0.1`  |

## Pruebas unitarias
> Aplica.
