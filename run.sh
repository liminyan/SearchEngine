#!/usr/bin/env bash
mvn package

java -jar target/233-1.0-SNAPSHOT-jar-with-dependencies.jar > res_20.txt

python3 judge.py