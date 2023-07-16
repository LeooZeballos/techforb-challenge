# Unicomer Bank API - TechForb Challenge

A simple API to manage bank accounts. This API was developed using Java 17, Spring Boot, Maven, and a PostgreSQL database.

## Requirements

- Java 17
- Maven
- PostgreSQL
- Docker (optional)

## Installation

### Steps

1. Clone the repository
2. Create a PostgreSQL database named `unicomerbank`. Skip this step if you are using Docker.
3. Create a `app.key` and `app.pub` files in the resources folder. You can generate them using openssl. More info below.
4. Run the application using `mvn spring-boot:run` or `docker-compose up` if you have Docker installed.
5. The application will be running on `http://localhost:8080`.
6. You can access the Swagger UI on `http://localhost:8080/swagger-ui.html`.

### OpenSSL

#### Generate a private key

```bash
openssl genpkey -algorithm RSA -out app.key
```

#### Generate a public key

```bash
openssl rsa -pubout -in app.key -out app.pub
```

## Usage

### Create a new account

```http
POST /api/v1/auth/register
```

| Parameter      | Type     | Description                                                            |
|:---------------|:---------|:-----------------------------------------------------------------------|
| `dni`          | `string` | **Required**. The user's DNI.                                          |
| `password`     | `string` | **Required**. The user's password.                                     |
| `email`        | `string` | **Required**. The user's email.                                        |
| `firstName`    | `string` | **Required**. The user's first name.                                   |
| `lastName`     | `string` | **Required**. The user's last name.                                    |
| `documentType` | `string` | **Required**. The user's document type. One of DNI, PASSPORT or CEDULA |


### Login

```http
POST /api/v1/auth/login
```

| Parameter      | Type     | Description                                                            |
|:---------------|:---------|:-----------------------------------------------------------------------|
| `dni`          | `string` | **Required**. The user's DNI.                                          |
| `password`     | `string` | **Required**. The user's password.                                     |
| `documentType` | `string` | **Required**. The user's document type. One of DNI, PASSPORT or CEDULA |


### Refresh token

```http
POST /api/v1/auth/refresh
```

| Parameter      | Type     | Description                         |
|:---------------|:---------|:------------------------------------|
| `refreshToken` | `string` | **Required**. The token to refresh. |


## License

[MIT Licence](https://choosealicense.com/licenses/mit/)

Copyright (c) 2023 Leonel Ayrton Zeballos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.