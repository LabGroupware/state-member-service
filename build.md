# 手動Build手順

``` sh
BUILD_VERSION=1.0.9
./gradlew clean build jibMultiBuild -PimageVersion=$BUILD_VERSION
docker push ablankz/nova-user-profile-service:$BUILD_VERSION-amd64
docker push ablankz/nova-user-profile-service:$BUILD_VERSION-arm64
```