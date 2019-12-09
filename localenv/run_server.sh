#!/bin/sh
docker --version
if [ $? -ne 0 ]; then
  echo "Installing docker"
  brew cask install docker
else
  echo "Docker is already installed!"
fi

docker-compose --version
if [ $? -ne 0 ]; then
  echo "Installing docker compose"
  brew install docker-compose
else
  echo "Docker compose is already installed!"
fi

docker stats --no-stream
if [ $? -ne 0 ]; then
  echo "Docker is not running"
  open --background -a Docker
else
  echo "Docker is already running"
fi

docker stats --no-stream
while [ $? -ne 0 ]
do
  echo "Waiting for docker to start"
  docker stats --no-stream
done

docker-compose up