# reactive-demo
Sample Spring Boot project using reactive approach: Webflux, r2dbc, resilient4j, ...

## Configuration

It uses a database to test [SpringData Reactive Repositories](https://docs.spring.io/spring-data/r2dbc/docs/1.3.3/reference/html/#reference).

By default, it uses an H2 instance:

```
dababase.vendor=h2
```

And this connection string:

```
database.connectionUrl=r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
```

So if you want to make a quick test, just start the application using `ReactiveDemoApplication` class and make a call to any of the endpoints.

For example, to create a new Wallet:

```sh
% curl -v -# -X POST http://localhost:8080/wallet/
*   Trying ::1:8080...
* Connected to localhost (::1) port 8080 (#0)
> POST /wallet/ HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.77.0
> Accept: */*
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 201 Created
< Content-Type: application/json
< Content-Length: 57
< 
* Connection #0 to host localhost left intact
{"id":"6cccd902-01a4-4a81-8166-933b2a109ecc","balance":0}
```

Oracle is also integrated, but it seems to have any problem with SpringData.

You can switch to Oracle just changing the vendor property:

```
dababase.vendor=oracle
```

Setting you database parameters:
```
database.host=localhost
database.port=1521
database.serviceName=ORCLCDB
database.user=jfcorugedo
```
And you need to set the password to an enviroment variable called `DB_PASSWORD` in your operating system.

If you want to run Oracle database using docker, take a look at this [Oracle repository](https://github.com/oracle/docker-images/blob/main/OracleDatabase/SingleInstance/README.md).
