[![Build Status](https://travis-ci.org/tushardhole/shortit.svg?branch=master)](https://travis-ci.org/tushardhole/shortit)
# shortit
URL shortening service
# Prerequisite
* docker
* docker-compose


# Installation
When docker is already installed:

    * Make sure docker engine is running
    * Go to localenv directory
    * enter command 'docker-compose up'
    
When docker is not installed:

    * Go to localenv directory
    * run './run_server.sh'
    
run_server.sh will install docker and docker-compose after that start the shortit service. The script is Mac OS specific.

  Once docker containers are up,

    * Go to browser and open, http://localhost:8080
    * Enter the URL to shorten, eg; http://www.google.com
    * On the result page you should see short url, please click on it to test redirection
