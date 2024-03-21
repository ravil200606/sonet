#!/bin/sh
echo 'Очистка файлов старой сборки...'
rm build/libs/*.*
rm docker/build/*.*
echo 'Очистка контейнера...'
sudo docker rm sonet
sudo docker rmi docker-spring-boot-postgres
echo 'Сборка...'
./gradlew build
cp build/libs/sonet-0.0.1-SNAPSHOT.jar docker/build/sonet-0.0.1-SNAPSHOT.jar
echo 'Запуск контейнера...' 
sudo docker compose up 
