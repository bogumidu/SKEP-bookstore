## 1. Instalation

### 1.1. With docker (recomended)

##### Requirements: 

 - docker
 - docker-compose
 - terminal of choice
 - postman/insomnia

Open terminal in base directory and run command `./run.sh`. Site will be running on localhost.

### 1.2. Install with InteliJ

First create mySQL database (using Wampserver or similar service) and link it to file via environment variable.

Run applications should look like this:

![idea64_9Q8jgYkBfd](https://user-images.githubusercontent.com/84717819/178144720-b6d6cf43-1801-4f6e-a68e-7b0dae8166ff.png)

- BASE_URL is main url app will be using
- DATASOURCE_PASSWORD is password to created mySQL database
- DATASOURCE_URL is url to mySQL database
- DATASOURCE_USERNAME is username to created mySQL database
- PORT is port app will be using

### 1.3. Create admin user:

 Admin user will be necessary to create Authors, Books and to manage orders. To create admin user first create normal user via `localhost/register` and then use request in postman to promote user to admin with "magic" displayed on spring server run terminal.

##### Magic example:

![3EpK58zz3j](https://user-images.githubusercontent.com/84717819/178144705-45125eac-ddde-4a75-a692-615dd1001899.png)

##### Request:

`http://localhost/api/auth/role?username=<username>&role=ADMIN&magic=<magic>`

## 2. Functionality

### 2.1. Orders

- registered users can place orders and see order history
- all users can create and modify shopping cart
- admin can see all orders and modify them

### 2.2. Search

- users can search books by query
- service uses Apache Lucene library to search books

### 2.3. Admin

- admin has access to admin panel `localhost/admin`
- admin can create new authors, books and modify order status

## 3. Grade features

App aims for 4.5 grade

    - [x] Users are split into roles: ADMIN, USER
    - [x] Users can browse books
    - [x] Admin can add Books and authors
    - [x] Users can create shopping cart
    - [x] Users can place orders
    - [x] Admin can modify order status
    - [x] Frontend uses Vue.js framework
