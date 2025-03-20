## POC Spring Boot Customer
`<autor>`: Miguel Rodrigo Armas Abt

### Prerrequisitos
* Java 17
* Maven 3.9+
* Plugin de Lombok
* Docker

### Despliegue con Docker

> ▶️ Generar el .jar
> ```
> mvn clean install
> ```
>
> ▶️ Crear imágenes
> ```
> docker build -t customer-v1:0.0.1 -f ./customer-v1/Dockerfile ./customer-v1
> docker build -t cryptography-v1:0.0.1 -f ./cryptography-v1/Dockerfile ./cryptography-v1
> ```
>
> ▶️ Ver imágenes
> ```
> docker image ls
> ```
>
> ▶️ Ejecutar contenedores
> ```
> docker run --name customer-v1 -p 8093:8093 customer-v1:0.0.1
> docker run --name cryptography-v1 -p 8094:8094 cryptography-v1:0.0.1
> ```
