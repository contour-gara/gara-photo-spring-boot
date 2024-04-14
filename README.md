# gara-photo-spring-boot

## 初期セットアップ

1. ビルドし、コンテナを起動します。

    ```shell
    (cd gara-photo-app-server && gradle clean build -x test)
    docker compose up --build -d
    ```

1. 認可 URL とチャレンジコードを取得します。

    ```shell
    curl http://localhost:8080/v1/oauth/url
    ```

1. 投稿したい Twitter アカウントにログインした状態で、取得した認可 URL にアクセスします。
1. アプリにアクセスを許可をクリックします。
1. リダイレクトされた URL からコードを抽出し、`/v1/oauth/token` へ POST します。

    ```shell
    curl -X POST http://localhost:8080/v1/oauth/token -H 'Content-Type: application/json' -d '{"code": "", "codeChallenge": ""}'
    ```

## API 一覧

|       |                                      | End point                |
|:------|:-------------------------------------|:-------------------------|
| OAuth | Create authorization end point       | GET /v1/oauth/url        |
|       | Fetch access token and refresh token | POST /v1/oauth/token     |
|       | Find access token                    | GET /v1/oauth/token      |
| Tweet | Tweet yesterday series               | POST /v1/tweet/yesterday |

## API 詳細

### GET /v1/oauth/url

認可 URL を取得します。

#### リクエスト形式

```
GET /v1/oauth/url
```

#### レスポンス形式

- Header

    ```
    HTTP/1.1 200
    ```

- body

  ```json
  {
    "url": "https://hogehoge",
    "codeChallenge": "hogehoge"
  }
    ```

### POST /v1/oauth/token

認可 URL から取得したコードをもとに、アクセストークンとリフレッシュトークンを取得し DB に保存します。

#### リクエスト形式

```
POST /v1/oauth/token
```

- body

    ```json
    {
      "code": "fugafuga",
      "codeChallenge": "hogehoge"
    }
    ```

#### レスポンス形式

- Header
    ```
    HTTP/1.1 204
    ```

### GET /v1/oauth/token

アクセストークンを取得します。アクセストークンの期限が切れている場合は、再取得して返します。

#### リクエスト形式

```
GET /v1/oauth/token
```

#### レスポンス形式

- Header

    ```
    HTTP/1.1 200
    ```

- body

  ```json
  {
    "accessToken": "piyopiyp"
  }
    ```

### POST /v1/tweet/yesterday

yesterday シリーズを投稿します。

#### リクエスト形式

```
POST /v1/tweet/yesterday
```

#### レスポンス形式

- Header
    ```
    HTTP/1.1 201
    ```

## 開発

### Run test

```shell
(cd gara-photo-app-server && gradle clean test)
```

### Sonarqube

```shell
docker compose --profile local up -d sonarqube
(cd gara-photo-app-server && gradle clean test sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=password)
```

### カスタム JRE

```shell
cd gara-photo-app-server
gradle build -x test
jdeps --ignore-missing-deps --multi-release 17 --print-module-deps --class-path 'BOOT-INF/lib/*' build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar
cd ..
```
出力内容を基に Dockerfile を変更する。 
