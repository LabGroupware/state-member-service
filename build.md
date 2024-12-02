# 手動Build手順

``` sh
./gradlew bootBuildImage --imageName=ablankz/nova-user-profile-service:1.0.1
docker push ablankz/nova-user-profile-service:1.0.1
```