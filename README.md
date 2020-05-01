# springboot-react-jwt-token

The goal of this project is to implement an application called `order-app` to manage orders. For it, we will implement a back-end application called `order-api` using [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) and a font-end application called `order-ui` using [ReactJS](https://reactjs.org/). Besides, we will use [`JWT Authentication`](https://en.wikipedia.org/wiki/JSON_Web_Token) to secure both applications.

## Applications

- **order-api**

  `Spring Boot` Web Java backend application that exposes a Rest API to create, retrieve and delete orders. If a user has `ADMIN` role he/she can also retrieve information of other users or delete them.
  
  The application secured endpoints can just be accessed if a valid JWT access token is provided.
  
  `order-api` stores its data in [`MySQL`](https://www.mysql.com/) database.

  `order-api` has the following endpoints

  | Endpoint                                                      | Secured | Roles           |
  | ------------------------------------------------------------- | ------- | --------------- |
  | `POST /auth/authenticate -d {"username","password"}`          | No      |                 |
  | `POST /auth/signup -d {"username","password","name","email"}` | No      |                 |
  | `GET /public/numberOfUsers`                                   | No      |                 |
  | `GET /public/numberOfOrders`                                  | No      |                 |
  | `GET /api/users/me`                                           | Yes     | `ADMIN`, `USER` |
  | `GET /api/users`                                              | Yes     | `ADMIN`         |
  | `GET /api/users/{username}`                                   | Yes     | `ADMIN`         |
  | `DELETE /api/users/{username}`                                | Yes     | `ADMIN`         |
  | `GET /api/orders`                                             | Yes     | `ADMIN`         |
  | `GET /api/orders/{id}`                                        | Yes     | `ADMIN`, `USER` |
  | `POST /api/orders -d {"description"}`                         | Yes     | `ADMIN`, `USER` |
  | `DELETE /api/orders/{id}`                                     | Yes     | `ADMIN`         |

- **order-ui**

  `ReactJS` frontend application where a user with role `USER` can create an order and retrieve a specific order. On the other hand, a user with role `ADMIN` as access to all secured endpoints.
  
  In order to access the application, a `user` or `admin` must login using his/her `username` and `password`. All the requests coming from `order-ui` to secured endpoints in `order-api` have the JWT access token. This token is generated when the `user` or `admin` logins.
  
  `order-ui` uses [`Semantic UI React`](https://react.semantic-ui.com/) as CSS-styled framework.

## Prerequisites

- **jq**

  In order to run some commands/scripts, you must have [`jq`](https://stedolan.github.io/jq) installed on you machine

## Start Environment

- Open a terminal and inside `springboot-react-jwt-token` root folder run
  ```
  docker-compose up -d
  ```
  
- Wait a little bit until `mysql` container is Up (healthy). You can check their status running
  ```
  docker-compose ps
  ```

## Running order-app using Maven & Npm

- **order-api**

  - Open a terminal and navigate to `springboot-react-jwt-token/order-api` folder

  - Run the following `Maven` command to start the application
    ```
    ./mvnw clean spring-boot:run
    ```

- **order-ui**

  - Open another terminal and navigate to `springboot-react-jwt-token/order-ui` folder

  - \[Optional\] Run the command below if you are running the application for the first time
    ```
    npm install
    ```

  - Run the `npm` command below to start the application
    ```
    npm start
    ```

## Applications URLs

| Application | URL                                   | Credentials                  |
| ----------- | ------------------------------------- | ---------------------------- |
| order-api   | http://localhost:8080/swagger-ui.html |                              |
| order-ui    | http://localhost:3000                 | `admin/admin` or `user/user` |

> **Note:** the credentials shown in the table are the ones already pre-defined. You can signup new users

## Demo

The gif below shows ...

## Testing order-api Endpoints

- **Manual Endpoints Test using Swagger**
  
  - Open a browser and access http://localhost:8080/swagger-ui.html. All endpoints with the lock sign are secured. In order to access them, you need a valid JWT access token.

  - Click on `auth-controller`, then on `POST /auth/authenticate` and, finally, on `Try it out`
  
  - Provide the `user` credentials `username` and `password` and click on `Execute` button
    ```
    { "password": "user", "username": "user" }
    ```
    It should return something like
    ```
    Code: 200
    { "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9..." }
    ```
    > **Note 1**: You can use the `admin` credentials to access more secured endpoints
    >
    > **Note 2**: The token will expire in **10 minutes**

  - Copy the `accessToken` value (**without** the double quotes)
  
  - Click on the `Authorize` button on the top of the page
  
  - On `Value` input field, paste the copied token prefixed by `Bearer` and a space
    ```
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9...
    ```
  
  - Click on `Authorize` and then on `Close`
  
  - To create an order, click on `order-controller`, then, click on `POST /api/orders` and on `Try it out`

  - Provide the `description` of the order
    ```
    { "description": "Buy two iPhones" }
    ```

  - Click on `Execute` button. It should return something like
    ```
    Code: 200
    {
      "id": "ccc4b36d-bda4-4f41-b6c2-f19c77f0243f",
      "description": "Buy two iPhones"
    }
    ```

- **Manual Endpoints Test using curl**

  - Open a terminal
  
  - Call `GET /public/numberOfUsers`
    ```
    curl -i localhost:8080/public/numberOfUsers
    ```
    It should return
    ```
    HTTP/1.1 200
    2
    ```

  - Call `GET /api/orders/{id}` without JWT access token
    ```
    curl -i localhost:8080/api/orders/6ce8cdf5-004d-4511-a6a1-604945246af8
    ```
    As for this endpoint a valid JWT access token is required, it should return
    ```
    HTTP/1.1 401
    ```

  - Call `POST /auth/authenticate` to get `user` JWT access token
    ```
    USER_ACCESS_TOKEN="$(curl -s -X POST http://localhost:8080/auth/login \
      -H 'Content-Type: application/json' \
      -d '{"username": "user", "password": "user"}' | jq -r .accessToken)"
    ```

  - Call again `GET /api/orders/{id}`, now with `user` JWT access token
    ```
    curl -i -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/orders/6ce8cdf5-004d-4511-a6a1-604945246af8
    ```
    It should return
    ```
    HTTP/1.1 200
    { "id":"6ce8cdf5-004d-4511-a6a1-604945246af8", "description":"Buy one MacBook Pro" }
    ```

  - Call `GET /api/users/me` to get more information about the `user`
    ```
    curl -i -H "Authorization: Bearer $USER_ACCESS_TOKEN" localhost:8080/api/users/me
    ```
    It should return
    ```
    HTTP/1.1 200
    {
      "id": 2, "username": "user", "name": "User", "email": "user@mycompany.com", "role": "USER",
      "orders": [ ... ]
    }
    ```

- **Automatic Endpoints Test**

  - Open a terminal and make sure you are in `springboot-react-jwt-token` root folder

  - Run the following script
    ```
    ./order-api/test-endpoints.sh
    ```
    It should return something like the output below, where it shows the http code for different requests
    ```
    GET auth/authenticate
    =====================
    admin access token
    ------------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImlhdCI6MTU4Nj..._ha2pM4LSSG3_d4exgA
    
    user access token
    -----------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImlhdCIyOSwian...Y3z9uwhuW_nwaGX3cc5A
    
    GET auth/signup
    ===============
    user2 access token
    ------------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImanRpIjoiYTMw...KvhQbsMGAlFov1Q480qg
    
    Authorization
    =============
                    Endpoints | without token |  user token |  admin token |
    ------------------------- + ------------- + ----------- + ------------ |
     GET public/numberOfUsers |           200 |         200 |          200 |
    GET public/numberOfOrders |           200 |         200 |          200 |
    ......................... + ............. + ........... + ............ |
            GET /api/users/me |           401 |         200 |          200 |
               GET /api/users |           401 |         403 |          200 |
         GET /api/users/user2 |           401 |         403 |          200 |
      DELETE /api/users/user2 |           401 |         403 |          200 |
    ......................... + ............. + ........... + ............ |
              GET /api/orders |           401 |         403 |          200 |
             POST /api/orders |           401 |         201 |          201 |
         GET /api/orders/{id} |           401 |         200 |          200 |
      DELETE /api/orders/{id} |           401 |         403 |          200 |
    ------------------------------------------------------------------------
     [200] Success -  [201] Created -  [401] Unauthorized -  [403] Forbidden
    ```

## Util Commands

- **MySQL**
  ```
  docker exec -it mysql mysql -uroot -psecret --database=orderdb
  show tables;
  ```

- **jwt.io**

  With [jwt.io](https://jwt.io) you can inform the JWT token and the online tool decodes the token, showing its header and payload.

## Shutdown

- Go to `order-api` and `order-ui` terminals and press `Ctrl+C` on each one

- To stop and remove docker-compose containers, networks and volumes, run the command below in `springboot-react-jwt-token` root folder
  ```
  docker-compose down -v
  ```

## How to upgrade order-ui dependencies to latest version

- In a terminal, make sure you are in `springboot-react-jwt-token/order-ui` folder

- Run the following commands
  ```
  npm i -g npm-check-updates
  ncu -u
  npm install
  ```

## References

- https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-2/#jwt-token-provider-authentication-filter-authentication-error-handler-and-userprincipal
- https://bezkoder.com/spring-boot-jwt-authentication/
- https://dev.to/keysh/spring-security-with-jwt-3j76
