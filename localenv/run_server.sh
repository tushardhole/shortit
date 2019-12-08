#!/bin/sh

dockerVersion=`docker --version`

if [[ $dockerVersion == *"Docker version"* ]]; then
  echo "Docker is already installed!"
else
  echo "Installing docker"
  brew cask install docker
fi

dockerComposeVersion=`docker-compose --version`

if [[ $dockerComposeVersion == *"docker-compose version"* ]]; then
  echo "Docker compose is already installed!"
else
  echo "Installing docker compose"
  brew cask install docker-compose
fi

dockerStat=`docker stats --no-stream`

if [ $? -ne 0 ]
then
  echo "Docker is not running"
  open --background -a Docker
else
  echo "Docker is already running"
fi

dockerStat=`docker stats --no-stream`
while [ $? -ne 0 ]
do
	echo "Waiting for docker to start"
	docker stats --no-stream
done

docker-compose up