# About product-review
API for products and reviews

 ## Product-Review is an API Restful web application with basic methods, such as:

- create
- show
- list

 ## Requirements

If you want use this application, you need preinstall some tools. Here a list of tools:

- Mysql 
  - Mysql engine [Install instructions](https://dev.mysql.com/doc/relnotes/mysql-installer/en/)
  
- Spring
   List of <a hfref="https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-system-requirements.html">Spring</a>  requeriments and modified version of Java:
   
   - Java >= 8
   - log4j Extension
   - ehcache Extension
   - mysql Extension
 
 - Eclipse [download](http://www.eclipse.org/downloads/)
    - import project and run
    
## Cloning and run project

for "*nxgx" environments you can get code with next commands:

```
user:yourpath$ git clone https://github.com/sebastian-e-campetella/product-review

```

### Remember setting your environment variables and create  user

mysql [create user][https://www.digitalocean.com/community/tutorials/crear-un-nuevo-usuario-y-otorgarle-permisos-en-mysql-es]
```
user=garba
password=garba1234
```
you want change some params into application.properties

.... and  enjoy!

## Testing

Run all unit test JUnit from project.

If you maybe run, you do use next code:

I have some trouble here, I need more time for reding and understand the behavior in java and spring

### You can create some produts and review by curl
```
user:path$ curl -i -X POST "http://localhost:8080/legacy/product/add?price=22&stock=3&used=false&description=3erer&name=fff&list_price=33.3"

user:path$ curl -i -X POST "http://localhost:8080/legacy/reviews/add?user=user_one&review=one review&product_id=1"

user:path$ curl -i -X GET  http://localhost:8080/products/1

```

## Thank you for considerations!
