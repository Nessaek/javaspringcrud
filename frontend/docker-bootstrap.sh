#!/usr/bin/env bash

	#This script installs java, sbt and the application
	#Run this script on a new EC2 instance as the user-data script, which is run by `root` on machine start-up.
	sudo yum update -y

    sudo yum install -y docker

sudo service docker start

docker run -p 3000:3000 nessaek/carriequotes