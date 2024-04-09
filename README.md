# gara-photo-spring-boot
## Quick start
```shell
gradle clean build
docker compose up --build -d
```
## Run test
```shell
gradle clean test
```
## Sonarqube
```shell
docker compose --profile local up -d sonarqube
gradle clean test sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=password
docker compose --profile local down
```
