# gara-photo-spring-boot

## Quick start

```shell
gradle clean build -x test
docker compose up --build -d
```

## API 一覧

|       |                                      | End point                |
|:------|:-------------------------------------|:-------------------------|
| OAuth | Create authorization end point       | GET /v1/oauth/url        |
|       | Fetch access token and refresh token | POST /v1/oauth/token     |
| Tweet | Tweet yesterday series               | POST /v1/tweet/yesterday |

## API 詳細

### GET /v1/oauth/url

認可エンドポイントを取得します。

#### リクエスト形式

```
GET /v1/oauth/url
```

### レスポンス形式

- Header
```
HTTP/1.1 200
```
- body
```json
{
  "url": "https://hogehoge",
  "code_challenge": "hogehoge"
}
```

### POST /v1/oauth/token

認可エンドポイントから取得したコードをもとに、アクセストークンとリフレッシュトークンを取得し DB に保存します。

#### リクエスト形式

```
POST /v1/oauth/token
```

### レスポンス形式

- Header
```
HTTP/1.1 204
```

### POST /v1/tweet/yesterday

yesterday シリーズを投稿します。

#### リクエスト形式

```
POST /v1/tweet/yesterday
```

### レスポンス形式

- Header
```
HTTP/1.1 201
```

## 開発

### Run test

```shell
gradle clean test
```

### Sonarqube

```shell
docker compose --profile local up -d sonarqube
gradle clean test sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=password
```

### カスタム JRE

```shell
jdeps --ignore-missing-deps --multi-release 17 --print-module-deps --class-path 'BOOT-INF/lib/*' build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar
```
出力内容を基に Dockerfile を変更する。 
