# Parser Application

![parser-application](parser-application.png)

[Requirements](requirements.md)


Stack
-------
- Java8
- Maven3
- Linux
- Docker-compose


Build
-----
```
mvn clean install
```


Run
---
```
docker-compose up -d
cd dist
sh run.sh
```

Reference
---
- https://spring.io/guides/gs/consuming-rest/
- https://spring.io/guides/gs/accessing-data-mysql/