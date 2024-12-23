# 手動Build手順

``` sh
./gradlew bootBuildImage --imageName=ablankz/nova-user-profile-service:1.0.5
docker push ablankz/nova-user-profile-service:1.0.5
```