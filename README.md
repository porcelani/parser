# wallethub-application

[Test Instructions](Java_MySQL_Test_Instructions.md)


Require
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


TODO
---
- parser.log.LogParserService.search 
- In ParserTests include verify of RequestParam;
- ArgumentValidator inside the server;
...