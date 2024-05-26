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

## yesterday シリーズ投稿

1. photo ディレクトリ配下に年月日のディレクトリ (yyyymmdd) を作成し、その配下に写真を配置します。
1. `/v1/tweet/yesterday` エンドポイントを POST します。

## 今後の開発予定

### Ktor 移行

業務でなれている Spring Boot を採用したが、軽量フレームワークである Ktor にもトライしたいと思う。

### ~~Twitter Client のテスト~~

~~twitter4j-v2 は、エンドポイントを変更できないため、テストできていない。
ライブラリをフォークし、テスト可能なようにエンドポイントを外から変更できるようにしたい。~~</br>
ライブラリをフォークし、修正した jar ファイルをこのリポジトリに配置した。
クライアントの UT を実装し、IT も実装した。

### yesterday シリーズの画像アップロード画面の作成

yesterday シリーズを投稿するための画像をアップロードするための画面を作成したい。
現状は、Dropbox に画像をおいて、rclone を使用し yesterday シリーズを投稿する前に photo ディレクトリを同期している。

### 任意のテキストで画像投稿する機能の作成

事前に写真をアップロードすることができず、yesterday シリーズの定時実行に遅れた場合でもツイートできるように、任意のテキストで画像つきツイートができる画面と API を作成したい。

### tweet に失敗した場合の通知

tweet に失敗した場合、どこかに通知したい。

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

- body

  ```json
  {
    "tweetId": "puyopuyo"
  }
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
./jdeps-spring-boot.sh build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar 21
cd ..
```
出力内容を基に Dockerfile を変更します。
`jdk.crypto.ec` は出力されないので、忘れずに入れて下さい。
