# State Based Pattern Implementation

## User Profile Service

### Prerequire
- [asdf](./setup_asdf.md)


### Setup
#### コマンドセットアップ
``` sh
asdf plugin add grpcurl
asdf plugin add kafka
asdf install
```

## Test
``` sh
grpcurl --plaintext -d '{"user_id": "user::2345178"}' localhost:9080 user.v1.UserService/GetUser
```