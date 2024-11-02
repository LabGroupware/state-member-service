# State Based Pattern Implementation

## User Profile Service

### Prerequire
- [asdf](./setup_asdf.md)

### Migrationファイル生成
``` sh
./gradlew generateMigrationFile -PmigrationName={ファイル内容} -Pdir={相対ディレクトリ}
```

具体例
``` sh
./gradlew generateMigrationFile -PmigrationName=create_user_profiles_table -Pdir=ddl
```

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