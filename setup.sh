#!/bin/bash
export TZ=Asia/Almaty
ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
  echo $TZ > /etc/timezone; \
  apt-get update && \
  apt-get install -y mysql-server openjdk-8-jre
