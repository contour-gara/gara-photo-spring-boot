# gara-photo-spring-boot

## Quick start

```shell
gradle clean build -x test
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
```

## カスタム JRE

```shell
jdeps --ignore-missing-deps --multi-release 17 --print-module-deps --class-path 'BOOT-INF/lib/*' build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar
```

出力内容を基に Dockerfile を変更する。 
