## POC Spring Boot Customer
`<autor>`: Miguel Rodrigo Armas Abt

## 📋 Pre requisitos
> ⚙️ **Instalar herramientas**<br>
> `Java 17+`, `Maven 3.9.1+`, `Docker`
>

## ▶️ Docker

⚙️ Crear imágenes
```
docker build -t customer-v1:0.0.1 -f ./customer-v1/Dockerfile ./customer-v1
docker build -t cryptography-v1:0.0.1 -f ./cryptography-v1/Dockerfile ./cryptography-v1
```

⚙️ Ver imágenes
```
docker image ls
```

⚙️ Ejecutar contenedores
```
docker run --rm -p 8093:8093 --name customer-v1  customer-v1:0.0.1
docker run --rm -p 8094:8094 --name cryptography-v1  cryptography-v1:0.0.1
```
